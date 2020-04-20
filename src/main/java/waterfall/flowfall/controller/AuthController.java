package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import waterfall.flowfall.model.User;
import waterfall.flowfall.model.requests.RegisterRequest;
import waterfall.flowfall.security.AuthFacade;
import waterfall.flowfall.security.AuthProvider;
import waterfall.flowfall.security.jwt.JwtResponse;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private AuthFacade authFacade;

    @Autowired
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        return ok(authFacade.authenticateAndGetToken(user));
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        authFacade.register(AuthProvider.LOCAL, registerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/register/verify")
    public ResponseEntity verify(@RequestParam String token, @RequestParam String redirectUri) {
        String redirectTo = UriComponentsBuilder.fromHttpUrl(redirectUri)
                .queryParam("verified", authFacade.verify(token))
                .build().toString();


        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, redirectTo)
                .build();
    }
}
