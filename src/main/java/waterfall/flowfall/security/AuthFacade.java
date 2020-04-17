package waterfall.flowfall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import waterfall.flowfall.model.GlobalRole;
import waterfall.flowfall.model.User;
import waterfall.flowfall.model.UserProfile;
import waterfall.flowfall.security.jwt.JwtProvider;
import waterfall.flowfall.security.jwt.JwtResponse;
import waterfall.flowfall.security.oauth2.exception.OAuth2AuthenticationException;
import waterfall.flowfall.security.oauth2.userinfo.OAuth2UserInfo;
import waterfall.flowfall.model.enums.UserGlobalRole;
import waterfall.flowfall.service.UserService;

import java.util.Arrays;


@Component
public class AuthFacade {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserService userService;

    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public AuthFacade(JwtProvider jwtProvider, UserService userService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public JwtResponse authenticate(User user) {
        Authentication authentication = null;

        if (user.getProvider() == null || user.getProvider().equals(AuthProvider.LOCAL)) {
            authentication = authenticateLocal(user.getEmail(), user.getPassword());
        } else {
            authentication = authenticateProvider(user.getEmail());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(user.getEmail());

        String jwt = jwtProvider.generateJwtToken(userDetails);

        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }

    public JwtResponse register(String provider, OAuth2UserInfo userInfo) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        user.setEmail(userInfo.getEmail());
        user.setProfile(new UserProfile(userInfo.getName()));
        user.setGlobalRoles(Arrays.asList(new GlobalRole(UserGlobalRole.USER)));

        return authenticate(userService.save(user));
    }

    public boolean verifyProvider(String provider) {
        try {
            AuthProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OAuth2AuthenticationException("Provider " + provider + " is not supported currently");
        }

        return true;
    }

    private Authentication authenticateLocal(String email, String password) {
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    private Authentication authenticateProvider(String email) {
        return new UsernamePasswordAuthenticationToken(email, null);
    }
}
