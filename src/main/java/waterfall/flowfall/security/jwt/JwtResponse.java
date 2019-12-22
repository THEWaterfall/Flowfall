package waterfall.flowfall.security.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private String type = "Bearer";

    private String accessToken;
    private String email;
    private Long id;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String accessToken, String email, Long id, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.email = email;
        this.id = id;
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
