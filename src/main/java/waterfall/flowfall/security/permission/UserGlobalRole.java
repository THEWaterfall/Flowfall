package waterfall.flowfall.security.permission;

public enum UserGlobalRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String globalRole;

    UserGlobalRole(String globalRole) {
    }

    public String getLiteral() {
        return this.globalRole;
    }
}
