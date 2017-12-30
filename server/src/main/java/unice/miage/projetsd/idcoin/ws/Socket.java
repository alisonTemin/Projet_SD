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
        config.setOrigin("http://127.0.0.1:8081");

        this.server = new SocketIOServer(config);
    }

    public void setListeners() {
        this.server.addConnectListener(
                (client) -> {
                    System.out.println("Client has Connected!");
                });

        this.server.addEventListener("bidEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Client said: " + message);
                });
    }

    public void start(){
        this.server.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.stop();
    }
}
