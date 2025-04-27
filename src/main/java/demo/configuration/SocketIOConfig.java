package demo.configuration;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.corundumstudio.socketio.Configuration;

@Slf4j
@org.springframework.context.annotation.Configuration // cz, already class named as Configuration (conflict)
public class SocketIOConfig {

    public SocketIOServer socketIOServer;
    @Value("${socketIOServer.host}")
    private String socketHost;
    @Value("${socketIOServer.port}")
    private int socketPort;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(socketHost);
        config.setPort(socketPort);
        config.setOrigin("*");
        this.socketIOServer = new SocketIOServer(config);
        return this.socketIOServer;
    }

    @PreDestroy
    public void stopServer() {
        if (this.socketIOServer != null) this.socketIOServer.stop();
        log.warn("web-socket has been stopped");
    }
}