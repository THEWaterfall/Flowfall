package waterfall.flowfall.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2UrlBuilder {

    private static Environment env;

    @Autowired
    public OAuth2UrlBuilder(Environment env) {
        this.env = env;
    }

    public static String buildCodeUrl(String provider, String redirectUri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                getPropertyForProvider(provider, "codeEndpoint"))
                    .queryParam("response_type", "code")
                    .queryParam("response_mode", "query")
                    .queryParam("client_id", getPropertyForProvider(provider, "clientId"))
                    .queryParam("scope", String.join(" ", getPropertyForProvider(provider, "scope").split(", ")))
                    .queryParam("redirect_uri", redirectUri);

        return builder.toUriString();
    }

    public static String buildTokenUrl(String provider, String code, String redirectUri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                getPropertyForProvider(provider, "tokenEndpoint"))
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("code", code)
                    .queryParam("client_id", getPropertyForProvider(provider, "clientId"))
                    .queryParam("client_secret", getPropertyForProvider(provider, "clientSecret"))
                    .queryParam("redirect_uri", redirectUri);

        return builder.toUriString();
    }

    public static String buildUserInfoUrl(String provider, String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                getPropertyForProvider(provider, "userInfoEndpoint"))
                   .queryParam("access_token", accessToken);

        return builder.toUriString();
    }

    private static String getPropertyForProvider(String provider, String property) {
        return env.getProperty("security.oauth2.client." + provider.toLowerCase() + "." + property);
    }
}
