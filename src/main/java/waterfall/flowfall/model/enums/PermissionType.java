package waterfall.flowfall.model.enums;

public enum PermissionType {
    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    INVITE("INVITE");

    private String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    public String getLiteral() {
        return this.permission;
    }
}
