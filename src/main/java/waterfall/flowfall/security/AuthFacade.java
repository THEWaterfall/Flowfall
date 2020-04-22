package waterfall.flowfall.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import waterfall.flowfall.enums.Template;
import waterfall.flowfall.model.GlobalRole;
import waterfall.flowfall.model.User;
import waterfall.flowfall.model.UserProfile;
import waterfall.flowfall.model.VerificationToken;
import waterfall.flowfall.model.enums.UserGlobalRole;
import waterfall.flowfall.model.requests.LoginRequest;
import waterfall.flowfall.model.requests.RegisterRequest;
import waterfall.flowfall.security.jwt.JwtProvider;
import waterfall.flowfall.security.jwt.JwtResponse;
import waterfall.flowfall.security.oauth2.exception.OAuth2AuthenticationException;
import waterfall.flowfall.service.UserService;
import waterfall.flowfall.service.VerificationTokenService;
import waterfall.flowfall.util.EmailUtils;

import java.nio.file.AccessDeniedException;
import java.util.*;

import static waterfall.flowfall.enums.VerifyTemplate.*;


@Component
public class AuthFacade {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserService userService;
    private VerificationTokenService verificationTokenService;

    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${water.verificationTokenExpirationInMinutes}")
    private int verificationTokenExpirationInMinutes;

    @Value("${water.supportEmail}")
    private String supportEmail;

    @Value("${water.apiUrl}")
    private String apiUrl;

    @Autowired
    public AuthFacade(JwtProvider jwtProvider, UserService userService, AuthenticationManager authenticationManager,
                      VerificationTokenService verificationTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    public JwtResponse authenticateAndGetToken(LoginRequest loginRequest) throws AccessDeniedException {
        return userService.findByEmail(loginRequest.getEmail())
            .map(user -> {
                authenticate(loginRequest);
                String jwt = jwtProvider.generateJwtToken(user);

                return new JwtResponse(jwt, user.getEmail(), user.isVerified());
            })
            .orElseThrow(() -> new AccessDeniedException("No user with email " + loginRequest.getEmail()));
    }

    public JwtResponse authenticateAndGetToken(User user) {
        authenticate(user);
        String jwt = jwtProvider.generateJwtToken(user);

        return new JwtResponse(jwt, user.getEmail(), user.isVerified());
    }

    public Authentication authenticate(LoginRequest loginRequest) {
        return authenticateWithCredentials(loginRequest.getEmail(), loginRequest.getPassword());
    }

    public Authentication authenticate(User user) {
        return authenticationWithoutCredentials(user.getEmail());
    }

    public Authentication authenticate(String email) throws AccessDeniedException {
       return userService.findByEmail(email)
                .map(user -> authenticationWithoutCredentials(email))
                .orElseThrow(() -> new AccessDeniedException("No user with email " + email));
    }

    public void register(AuthProvider provider, RegisterRequest registerRequest) {
        User user = new User();
        user.setProfile(new UserProfile(registerRequest.getFullname()));
        user.setProvider(provider);
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setGlobalRoles(Collections.singletonList(new GlobalRole(UserGlobalRole.USER)));
        user.setVerified(false);

        sendVerificationToken(userService.save(user), registerRequest.getRedirectUri());
    }

    public boolean verify(String token) {
        return verificationTokenService.findByToken(token)
                .map(foundToken -> {
                    verificationTokenService.delete(foundToken);

                    boolean isExpired = foundToken.getExpirationDate().before(new Date());
                    if (isExpired) {
                        return false;
                    }

                    User user = foundToken.getUser();
                    user.setVerified(true);
                    userService.update(user);

                    return true;
                })
                .orElse(false);
    }

    public boolean verifyProvider(String provider) {
        try {
            AuthProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OAuth2AuthenticationException("Provider " + provider + " is not supported currently");
        }

        return true;
    }

    private void sendVerificationToken(User user, String redirectUri) {
        VerificationToken verificationToken =
                new VerificationToken(user, UUID.randomUUID().toString(), verificationTokenExpirationInMinutes);
        verificationTokenService.save(verificationToken);

        Map<String, String> valuesToBind = new HashMap<>();
        valuesToBind.put(URL.getLiteral(), apiUrl + "/auth/register/verify");
        valuesToBind.put(FULLNAME.getLiteral(), user.getProfile().getFullname());
        valuesToBind.put(TOKEN.getLiteral(), verificationToken.getToken());
        valuesToBind.put(REDIRECT_URI.getLiteral(), redirectUri);

        EmailUtils.send(user.getEmail(), supportEmail, "Verification",
                EmailUtils.renderTemplate(Template.VERIFY, valuesToBind));
    }

    private Authentication authenticateWithCredentials(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private Authentication authenticationWithoutCredentials(String email) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
