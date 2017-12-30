package unice.miage.projetsd.idcoin.ws;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class Socket {

    private SocketIOServer server;
    private String hostname;
    private int port;
    private Configuration config;

    public Socket(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void init(){
        System.out.println("Starting SocketIO server on : " + this.hostname + ":" + this.port);

        this.config = new Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        config.getSocketConfig().setReuseAddress(true);

        this.server = new SocketIOServer(config);
    }

    public void setListeners() {
        this.server.addConnectListener(
                (client) -> {
                    System.out.println("User connected");
                });

        this.server.addEventListener("bidEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Bid event received : " + message);
                });

        this.server.addDisconnectListener((client) -> {
            System.out.println("User has disconnected");
        });
    }

    public void start(){
        this.server.start();
    }
}
