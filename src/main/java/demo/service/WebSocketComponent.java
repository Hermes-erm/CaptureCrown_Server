package demo.service;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import demo.configuration.SocketIOConfig;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Slf4j
@Component
public class WebSocketComponent {

    private final SocketIOServer server;

    public ObjectMapper objectMapper;

    public String poseEvent = "player-pose";

    public String newComer = "new-player";

    public String playerLeft = "player-left";

    public String onLobby = "on-lobby";

    private HashMap<UUID, PayLoad> players = new HashMap<>();

//    @Autowired
//    private Gson gson; // to serialize and de-serialize objects

//    private Gson

    public WebSocketComponent(SocketIOServer server) {
        this.server = server;
        this.server.start();
        this.objectMapper = new ObjectMapper();
//        this.server.addConnectListener(client -> log.info("client {} connected", client.getSessionId().toString().substring(33)));
        // this::playerDisconnected (method reference instead of playerDisconnected(client)), just a reference of method instead of directly invoking method in the ((func)->{}) parameter
        this.server.addDisconnectListener(this::playerDisconnected);
        this.server.addEventListener(this.poseEvent, PayLoad.class, onMessageReceived(this.poseEvent));
        this.server.addEventListener(this.newComer, PayLoad.class, onNewPlayer(this.newComer));
    }

    public String objToJson(Object obj) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(obj);
    }

    public DataListener<PayLoad> onMessageReceived(String eventName) {
        return (client, data, ackRequest) -> {
//            String payload = this.objToJson(data);
            log.info("Message received from {} => {}", client.getSessionId().toString().substring(33), this.objToJson(data));
            this.server.getBroadcastOperations().sendEvent(eventName, client, data);
        };
    }

    public DataListener<PayLoad> onNewPlayer(String eventName) {
        return (client, data, ackRequest) -> {
//            String payload = this.objToJson(data);
            UUID playerId = client.getSessionId();
            String name = data.getName();
            log.info("new player arrived {}", name);
            client.sendEvent(this.onLobby, this.players.values());
            this.players.put(playerId, data); // name
            this.server.getBroadcastOperations().sendEvent(eventName, client, data); // payload
        };
    }

    public void playerDisconnected(SocketIOClient client) {
        UUID playerId = client.getSessionId();
        this.server.getBroadcastOperations().sendEvent(this.playerLeft, client, this.players.get(playerId));
        if (this.players.get(playerId) != null)
            log.info("client {} disconnected", this.players.get(playerId).getName()); // client.getSessionId().toString().substring(33)
        this.players.remove(playerId);
    }
}
