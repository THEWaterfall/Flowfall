package waterfall.flowfall.security.oauth2.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import waterfall.flowfall.security.oauth2.OAuth2UrlBuilder;
import waterfall.flowfall.util.CookieUtils;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class OAuth2ExceptionHandler {

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity exception(HttpServletRequest request, OAuth2AuthenticationException exception) throws Exception {
        String redirectUri = CookieUtils.getCookie(request, "redirect_uri").get().getValue();

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION,
                        OAuth2UrlBuilder.buildFailureResponseUrl(exception.getMessage(), redirectUri))
                .build();
    }
}
