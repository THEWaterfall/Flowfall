package waterfall.flowfall.security.oauth2;

import waterfall.flowfall.security.AuthProvider;
import waterfall.flowfall.security.oauth2.exception.OAuth2AuthenticationException;
import waterfall.flowfall.security.oauth2.userinfo.FacebookOAuth2UserInfo;
import waterfall.flowfall.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import waterfall.flowfall.security.oauth2.userinfo.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        if (provider.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (provider.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
           throw new OAuth2AuthenticationException("Provider is not supported: " + provider);
        }
    }

}
