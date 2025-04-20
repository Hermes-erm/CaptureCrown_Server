package demo.service;

import com.corundumstudio.socketio.SocketIOServer;
import demo.configuration.SocketIOConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketComponent {

    private final SocketIOServer server;

    public WebSocketComponent(SocketIOServer server) {
        this.server = server;
        this.server.addConnectListener(client -> log.info("client {} connected", client.getSessionId()));
        this.server.addDisconnectListener(client -> log.info("client {} disconnected", client.getSessionId()));
    }
}
