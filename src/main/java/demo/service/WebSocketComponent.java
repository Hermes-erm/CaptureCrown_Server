package demo.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import demo.configuration.SocketIOConfig;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketComponent {

    private final SocketIOServer server;

    public WebSocketComponent(SocketIOServer server) {
        this.server = server;
        this.server.start();
        this.server.addConnectListener(client -> log.info("client {} connected", client.getSessionId().toString().substring(33)));
        this.server.addDisconnectListener(client -> log.info("client {} disconnected", client.getSessionId().toString().substring(33)));
        this.server.addEventListener("sendMessage", PayLoad.class, onMessageReceived());
        this.server.stop();
    }

    public DataListener<PayLoad> onMessageReceived() {
        return (client, data, ackRequest) -> {
            log.info("Message {}", data);
        };
    }
}
