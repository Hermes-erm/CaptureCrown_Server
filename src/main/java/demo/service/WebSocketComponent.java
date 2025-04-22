package demo.service;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.configuration.SocketIOConfig;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class WebSocketComponent {

    private final SocketIOServer server;

    public ObjectMapper objectMapper;

    public WebSocketComponent(SocketIOServer server) {
        this.server = server;
        this.server.start();
        this.objectMapper = new ObjectMapper();
        this.server.addConnectListener(client -> log.info("client {} connected", client.getSessionId().toString().substring(33)));
        this.server.addDisconnectListener(client -> log.info("client {} disconnected", client.getSessionId().toString().substring(33)));
        this.server.addEventListener("sendMessage", PayLoad.class, onMessageReceived());
    }

    public DataListener<PayLoad> onMessageReceived() {
        return (client, data, ackRequest) -> {
            log.info("Message received from clientId  {} => {}", client.getSessionId().toString().substring(33), this.objToJson(data));
            this.server.getBroadcastOperations().sendEvent("receive_message", client, data);
        };
    }

    public String objToJson(Object obj) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(obj);
    }
}
