package unice.miage.projetsd.idcoin.ws;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import com.sun.deploy.net.proxy.RemoveCommentReader;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Input;
import unice.miage.projetsd.idcoin.blockchain.Transaction;
import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.events.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
    private Blockchain blockchain;

    /**
     * Socket constructor.
     * @param hostname localhost
     * @param port 9003
     */
    public Socket(String hostname, int port, Blockchain blockchain){
        this.hostname = hostname;
        this.port = port;
        this.eW = new EventWrapper();
        this.blockchain = blockchain;
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
         * @listens publickey
         */
        this.server.addEventListener("publickey", String.class, (client, message, ackRrequest) -> {
            PubKeyEvent newKey = (PubKeyEvent) eW.convertEvent(message, PubKeyEvent.class);
            System.out.println("Received new public key for " + newKey.getEmitter() + " | Key :"+newKey.getKey());

            String path =  "keys/" + newKey.getEmitter()+ ".pem";
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"))) {
                writer.write(newKey.getKey());
            }
        });

        /*
         * @listens login
         */
        this.server.addEventListener("login", String.class,
                (client, message, ackRequest) -> {
                    // Wrapping stringified json
                    LoginEvent loginEvent = (LoginEvent) eW.convertEvent(message, LoginEvent.class);

                    System.out.println("Starting new bid ceremony with item : " + loginEvent.getItem() + " | price : " + loginEvent.getPrice() + " for : "+ loginEvent.getUsername());
                    System.out.println("Waiting for bids");
                    long objectId = this.db.insertObject(loginEvent.getItem(), Integer.parseInt(loginEvent.getPrice()));
                    // Check if user is in database
                    if(this.db.insertSell(loginEvent.getUsername(), objectId)){
                        // Reply to client, he is authenticated now !
                        client.sendEvent("loginSuccess", "ok");
                    }
                });


        /*
         * @listens place
         * Mise en enchère d'un objet
         */
        this.server.addEventListener("place", String.class,
                (client, message, ackRequest) -> {
                    client.sendEvent("placeSuccess", this.blockchain.getBlocks());
                    this.db.addBid(message);
                    if(this.db.getBids().size() == 2){
                        System.out.println("Asking to mine");
                        client.sendEvent("mine", this.blockchain.getBlocks());
                        this.askEverybodyToMineExcept(client);
                    }
                });

        /*
         * @listens removeObjectEvent
         * Objet retiré des enchères(manuellement par l'utilisateur qui l'a posté)
         */
        this.server.addEventListener("removeObjectEvent", String.class,
                (client, message, ackRequest) -> {
                    RemoveEvent removeEvent = (RemoveEvent) eW.convertEvent(message, RemoveEvent.class);

                    if(this.db.deleteSell(removeEvent.getId()))
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


        this.server.addEventListener("register", String.class,
                (client, message, ackRequest) -> {
                    // Wrapping stringified json
                    RegisterEvent registerEvent = (RegisterEvent) eW.convertEvent(message, RegisterEvent.class);

                    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
                    SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                    keyGenerator.initialize(1024, random);
                    KeyPair pair = keyGenerator.generateKeyPair();
                    PrivateKey privKey = pair.getPrivate();
                    PublicKey pubKey = pair.getPublic();

                    //System.out.println("Regiter privKey" + privKey.toString());
                    // Check if user is in database
                    // Reply to client, he is authenticated now !
                    client.sendEvent("registerSuccess", privKey.getFormat());
                });



        /*
         * @listens bidEvent
         * Placer enchère
         */
        this.server.addEventListener("bidEvent", String.class,
                (client, message, ackRequest) -> {
                    int blocksNb = blockchain.getBlocks().size();
                    Block block = new Block(blockchain.getBlocks().size(), blockchain.getBlocks().get(blocksNb).getHash());
                    Transaction tx = new Transaction(blockchain.getTransactions().size(), block.getHash(), "bid");
                    Input i = new Input(tx.toHash(), 1, null, 200);
                    tx.addInput(i);

                    this.blockchain.addTransaction(tx);

                    this.blockchain.addBlock(block);
                    System.out.println("Bid event received : " + message);
                });

        /*
         * @listens disconnect
         */
        this.server.addDisconnectListener((client) -> {
            System.out.println("User has disconnected");
        });
    }

    private void askEverybodyToMineExcept(SocketIOClient client){
        for(SocketIOClient cli : this.server.getAllClients()){
            if(!cli.equals(client)){
                cli.sendEvent("mine", this.blockchain.getBlocks());
            }
        }
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
