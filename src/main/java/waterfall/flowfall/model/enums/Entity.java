package waterfall.flowfall.model.enums;

public enum Entity {
    BOARD("BOARD"),
    COLUMN("COLUMN"),
    ROW("ROW"),
    MESSAGE("MESSAGE");

    private String entity;

    Entity(String entity) {
        this.entity = entity;
    }

    public String getLiteral() {
        return this.entity;
    }
}
