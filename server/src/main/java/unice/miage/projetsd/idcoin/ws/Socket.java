package unice.miage.projetsd.idcoin.ws;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.events.EventWrapper;
import unice.miage.projetsd.idcoin.events.LoginEvent;
import unice.miage.projetsd.idcoin.events.RegisterEvent;

import java.security.*;

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

                    // Check if user is in database
                    if(this.db.isValidUser(loginEvent)){
                        // Reply to client, he is authenticated now !
                        client.sendEvent("loginSuccess", "ok");
                    }
                });


        /*
         * @listens placeObjectEvent
         * Mise en enchère d'un objet
         */

        this.server.addEventListener("placeObjectEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Object placed in bid : " + message);
                });

        /*
         * @listens removeObjectEvent
         * Objet retiré des enchères(manuellement par l'utilisateur qui l'a posté)
         */

        this.server.addEventListener("removeObjectEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Object removed from the bid : " + message);
                });

        /*
         * @listens wonBidEvent
         * Objet retiré des enchères (enchère remportée par un client)
         */

        this.server.addEventListener("wonBidEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Bid was won : " + message);
                });

        /*
         * @listens cancelBidEvent
         * Annuler enchère déjà placée
         */

        this.server.addEventListener("cancelBidEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Bid event received : " + message);
                });

        /*
         * @listens buyItNowEvent
         * Achat immédiat
         */

        this.server.addEventListener("buyItNowEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Object bought with 'Buy it now' : " + message);
                });

        /*
         * @listens findObjectEvent
         * Recherche d'objet
         */

        this.server.addEventListener("findObjectEvent", String.class,
                (client, message, ackRequest) -> {
                    System.out.println("Object found : " + message);
                });

        /*
         * @listens registerEvent
         * Inscription réussie
         */


        this.server.addEventListener("registerEvent", String.class,
                (client, message, ackRequest) -> {
                    // Wrapping stringified json
                    RegisterEvent registerEvent = (RegisterEvent) eW.convertEvent(message, RegisterEvent.class);

                    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
                    SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                    keyGenerator.initialize(1024, random);
                    KeyPair pair = keyGenerator.generateKeyPair();
                    PrivateKey privKey = pair.getPrivate();
                    PublicKey pubKey = pair.getPublic();

                    // Check if user is in database
                    if(this.db.isValidRegistration(registerEvent)){
                        // Reply to client, he is authenticated now !
                        client.sendEvent("registration Success", "ok");
                        client.sendEvent("private key", privKey);
                    }
                });



        /*
         * @listens bidEvent
         * Placer enchère
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
     * @param db database (biddata)
     */
    public void setDb(Database db) {
        this.db = db;
    }
}
