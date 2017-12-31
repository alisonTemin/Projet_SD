package unice.miage.projetsd.idcoin.ws;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.events.EventWrapper;
import unice.miage.projetsd.idcoin.events.LoginEvent;

/**
 * SocketIO
 */
public class Socket {

    private SocketIOServer server;
    private String hostname;
    private int port;
    private Configuration config;
    private EventWrapper eW;
    private Database db;

    /**
     * Socket constructor.
     * @param hostname localhost
     * @param port 9003
     */
    public Socket(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        this.eW = new EventWrapper();
    }

    /**
     * Start socketIO server
     */
    public void init(){
        System.out.println("Starting SocketIO server on : " + this.hostname + ":" + this.port);

        this.config = new Configuration();
        config.setHostname(hostname);
        config.setPort(port);

        // add the ability to reload server instantly
        config.getSocketConfig().setReuseAddress(true);

        this.server = new SocketIOServer(config);
    }

    /**
     * Listeners
     */
    public void setListeners() {
        /*
         * @listens connect
         */
        this.server.addConnectListener(
                (client) -> {
                    System.out.println("User connected");
                });

        /*
         * @listens login
         */
        this.server.addEventListener("login", String.class,
                (client, message, ackRequest) -> {
                    // Wrapping stringified json
                    LoginEvent loginEvent = (LoginEvent) eW.convertEvent(message, LoginEvent.class);

                    // Check if user is in Database
                    if(this.db.isValidUser(loginEvent)){
                        // Reply to client, he is authenticated now !
                        client.sendEvent("loginSuccess", "ok");
                    }
                });

        /*
         * @listens bidEvent
         */
        this.server.addEventListener("bidEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Bid event received : " + message);
                });

        /*
         * @listens disconnect
         */
        this.server.addDisconnectListener((client) -> {
            System.out.println("User has disconnected");
        });
    }

    /**
     * Start socketIO server
     */
    public void start(){
        this.server.start();
    }

    /**
     * Set database instance
     * @param db Database (biddata)
     */
    public void setDb(Database db) {
        this.db = db;
    }
}
