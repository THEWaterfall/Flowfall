package waterfall.flowfall.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import waterfall.flowfall.model.RowMessage;
import waterfall.flowfall.service.RowMessageService;
import waterfall.flowfall.websocket.WebSocketMessage;

@RestController
@CrossOrigin("*")
public class RowMessageSocketController {

    private RowMessageService rowMessageService;

    @Autowired
    public RowMessageSocketController(RowMessageService rowMessageService) {
        this.rowMessageService = rowMessageService;
    }

    @MessageMapping("/send/{rowId}")
    @SendTo("/message/{rowId}")
    public WebSocketMessage<RowMessage> sendMessage(@Payload WebSocketMessage<RowMessage> message, @DestinationVariable Long rowId) {
        rowMessageService.save(message.getMessage());
        return message;
    }

    @MessageMapping("/delete/{rowId}")
    @SendTo("/message/{rowId}")
    public WebSocketMessage<RowMessage> deleteMessage(@Payload WebSocketMessage<RowMessage> message, @DestinationVariable Long rowId) {
        rowMessageService.delete(message.getMessage());
        return message;
    }
}
