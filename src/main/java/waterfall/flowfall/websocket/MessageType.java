package waterfall.flowfall.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageType {
    @JsonProperty("SEND")
    SEND("SEND"),

    @JsonProperty("DELETE")
    DELETE("DELETE");

    private String type;

    MessageType(String type) {
        this.type = type;
    }

    String getLiteral() {
        return this.type;
    }
}
