package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.security.AuthFacade;
import waterfall.flowfall.security.jwt.JwtResponse;
import waterfall.flowfall.security.oauth2.OAuth2Facade;
import waterfall.flowfall.security.oauth2.OAuth2UrlBuilder;
import waterfall.flowfall.util.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping(value = "/api/v1/oauth2")
public class OAuth2Controller {

    @Value("${security.oauth2.redirectUri}")
    private String oauth2RedirectUri;

    private OAuth2Facade oauth2Facade;
    private AuthFacade authFacade;

    @Autowired
    public OAuth2Controller(OAuth2Facade oauth2Facade, AuthFacade authFacade) {
        this.oauth2Facade = oauth2Facade;
        this.authFacade = authFacade;
    }

    @GetMapping(value = "/code")
    public ResponseEntity oauth(HttpServletResponse response, HttpServletRequest request,
                                @RequestParam(name="provider") String provider,
                                @RequestParam(name="redirect_uri") String redirectUri) {
        CookieUtils.addCookie(response, "redirect_uri", redirectUri, 180);
        CookieUtils.addCookie(response, "provider", provider, 180);

        authFacade.verifyProvider(provider);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, OAuth2UrlBuilder.buildCodeUrl(provider, oauth2RedirectUri))
                .build();
    }

    @GetMapping(value="/token")
    public ResponseEntity oauth2(HttpServletRequest request, @RequestParam(name="code") String code) {
        String redirectUri = CookieUtils.getCookie(request, "redirect_uri").get().getValue();
        String provider = CookieUtils.getCookie(request, "provider").get().getValue();

        JwtResponse jwtResponse = oauth2Facade.authenticate(provider, code);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, OAuth2UrlBuilder.buildSuccessResponseUrl(jwtResponse, redirectUri))
                .build();
    }
}
