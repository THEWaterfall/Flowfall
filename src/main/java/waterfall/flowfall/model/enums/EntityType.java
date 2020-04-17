package waterfall.flowfall.model.enums;

public enum EntityType {
    BOARD("BOARD"),
    COLUMN("COLUMN"),
    ROW("ROW"),
    MESSAGE("MESSAGE");

    private String entity;

    EntityType(String entity) {
        this.entity = entity;
    }

    public String getLiteral() {
        return this.entity;
    }
}
