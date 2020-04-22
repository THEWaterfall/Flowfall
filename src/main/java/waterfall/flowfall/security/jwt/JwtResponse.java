package waterfall.flowfall.security.jwt;

public class JwtResponse {
    private String type = "Bearer";

    private String accessToken;
    private String email;
    private boolean enabled;

    public JwtResponse(String accessToken, String email, boolean enabled) {
        this.accessToken = accessToken;
        this.email = email;
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
