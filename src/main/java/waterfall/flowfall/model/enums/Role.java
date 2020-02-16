package waterfall.flowfall.model.enums;

public enum Role {
    BOARD_OWNER("BOARD_OWNER"),
    BOARD_COLLABORATOR("BOARD_COLLABORATOR");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getLiteral() {
        return this.role;
    }
}
