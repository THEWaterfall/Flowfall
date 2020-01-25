package waterfall.flowfall.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageType {
    @JsonProperty("send")
    SEND("send"),

    @JsonProperty("delete")
    DELETE("delete");

    private String type;

    MessageType(String type) {
        this.type = type;
    }

    String getLiteral() {
        return this.type;
    }
}
