package waterfall.flowfall.controller;

public class AuthRequest {
    public String grant_type;
    public String code;
    public String client_id;
    public String secret_code;
    public String redirect_url;

    public AuthRequest(String grant_type, String code, String client_id, String secret_code, String redirect_url) {
        this.grant_type = grant_type;
        this.code = code;
        this.client_id = client_id;
        this.secret_code = secret_code;
        this.redirect_url = redirect_url;
    }
}
