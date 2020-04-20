package waterfall.flowfall.enums;

public enum VerifyTemplate {
    URL("url"),
    FULLNAME("fullname"),
    TOKEN("token"),
    REDIRECT_URI("redirectUri");

    private String variable;

    VerifyTemplate(String variable) {
        this.variable = variable;
    }

    public String getLiteral() {
        return variable;
    }
}
