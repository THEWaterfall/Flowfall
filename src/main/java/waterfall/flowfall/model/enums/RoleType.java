package waterfall.flowfall.model.enums;

public enum RoleType {
    BOARD_OWNER("BOARD_OWNER"),
    BOARD_COLLABORATOR("BOARD_COLLABORATOR");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getLiteral() {
        return this.role;
    }
}
