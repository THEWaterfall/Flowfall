package waterfall.flowfall.enums;

public enum Template {
    VERIFY("verify");

    private String template;

    Template(String template) {
        this.template = template;
    }

    public String getLiteral() {
        return template;
    }
}
