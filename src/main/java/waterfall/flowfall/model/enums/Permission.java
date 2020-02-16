package waterfall.flowfall.model.enums;

public enum Permission {
    READ("READ"),
    WRITE("WRITE"),
    UPDATE("UPDATE"),
    INVITE("INVITE");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getLiteral() {
        return this.permission;
    }
}
