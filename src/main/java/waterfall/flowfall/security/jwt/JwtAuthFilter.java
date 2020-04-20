package waterfall.flowfall.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import waterfall.flowfall.security.AuthFacade;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthFacade authFacade;

    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${security.allowedApis}")
    private List<String> allowedApis;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!isAllowedApi(request.getServletPath())) {
            String token = jwtProvider.getJwtFromHeader(request.getHeader(jwtProvider.JWT_HEADER_NAME));

            if (token == null) {
                throw new AccessDeniedException("Authorization token is not specified");
            }

            if (!jwtProvider.validateJwtToken(token)) {
                throw new AccessDeniedException("Authorization token is not valid");
            }

            Authentication auth = authFacade.authenticate(jwtProvider.getEmailFromJwtToken(token));
            ((AbstractAuthenticationToken) auth).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAllowedApi(String api) {
        boolean allowed = false;

        for (String allowedApi: allowedApis) {
            if (allowedApi.endsWith("**")) {
                allowed = api.startsWith(allowedApi.substring(0, allowedApi.length() - 3));
            } else {
                allowed = api.equals(allowedApi);
            }

            if (allowed) {
                break;
            }
        }

        return allowed;
    }
}
