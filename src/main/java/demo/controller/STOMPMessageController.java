package demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessageController {

    // /app/senMessage -> from client
    @MessageMapping("/topic/sendMessage")
    @SendTo("/receiveMessage")
    public String sendMessage(String message) {
        return message;
    }
}
