package waterfall.flowfall.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import waterfall.flowfall.model.GlobalRole;
import waterfall.flowfall.model.User;
import waterfall.flowfall.model.UserProfile;
import waterfall.flowfall.model.enums.UserGlobalRole;
import waterfall.flowfall.security.AuthFacade;
import waterfall.flowfall.security.AuthProvider;
import waterfall.flowfall.security.AuthUrlBuilder;
import waterfall.flowfall.security.jwt.JwtResponse;
import waterfall.flowfall.security.oauth2.userinfo.OAuth2UserInfo;
import waterfall.flowfall.service.UserService;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2Facade {

    private RestTemplate restTemplate;
    private UserService userService;
    private AuthFacade authFacade;

    @Value("${security.oauth2.redirectUri}")
    private String oauth2RedirectUri;

    @Autowired
    public OAuth2Facade(UserService userService, AuthFacade authFacade) {
        this.restTemplate =  new RestTemplate();
        this.userService = userService;
        this.authFacade = authFacade;
    }

    public JwtResponse authenticate(String provider, String code) {
        Map map = restTemplate.exchange(AuthUrlBuilder.buildTokenUrl(provider, code, oauth2RedirectUri),
                HttpMethod.POST, null, Map.class).getBody();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider,
                restTemplate
                    .exchange(
                        AuthUrlBuilder.buildUserInfoUrl(provider, map.get("access_token").toString()),
                        HttpMethod.GET, null, Map.class)
                    .getBody()
        );

        Optional<User> optionalUser = userService.findByEmail(userInfo.getEmail());

        if (optionalUser.isPresent()) {
            return authFacade.authenticateAndGetToken(optionalUser.get());
        } else {
            return register(provider, userInfo);
        }
    }

    public JwtResponse register(String provider, OAuth2UserInfo userInfo) {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
        user.setEmail(userInfo.getEmail());
        user.setProfile(new UserProfile(userInfo.getName()));
        user.setGlobalRoles(Arrays.asList(new GlobalRole(UserGlobalRole.USER)));
        user.setVerified(true);

        return authFacade.authenticateAndGetToken(userService.save(user));
    }
}
