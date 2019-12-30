package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.User;
import waterfall.flowfall.security.UserPrincipal;
import waterfall.flowfall.security.jwt.JwtProvider;
import waterfall.flowfall.security.jwt.JwtResponse;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        return ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getId(), userDetails.getAuthorities()));
    }
}
