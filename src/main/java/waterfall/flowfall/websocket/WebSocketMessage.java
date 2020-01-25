package waterfall.flowfall.websocket;

public class WebSocketMessage<T> {
    private MessageType type;
    private T message;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
