package unice.miage.projetsd.idcoin.database;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Transaction;
import unice.miage.projetsd.idcoin.events.LoginEvent;
import unice.miage.projetsd.idcoin.events.RegisterEvent;

public class Database{
    /**
     * MongoClient
     */
    private MongoClient client;

    /**
     * MongoDatabase
     */
    private MongoDatabase db;

    /**
     * Every usable collection name
     */
    private String[] collectionsNames = new String[]{"client","objet","enchere", "enchereTerminee", "users"};

    /**
     * Connection uri on Mlab
     */
    private String dsn = "mongodb://test:test@ds163016.mlab.com:63016/biddata";

    /**
     * MongoClient URI based on DSN
     */
    private MongoClientURI uri;

    /**
     * Instance collections
     */
    private ArrayList<MongoCollection<Document>> collections;

    /**
     * database constructor.
     *
     * @param dbName name
     */
    public Database(String dbName) {
        this.uri = new MongoClientURI(this.dsn);
        this.client = new MongoClient(this.uri);
        this.db = this.client.getDatabase(dbName);
        this.collections = new ArrayList<>();
    }

    /**
     * Add every collections to current instance
     */
    public void importDb() {
        for(String coll : this.collectionsNames){
            collections.add(this.db.getCollection(coll));
        }
    }

    /**
     * Check if user does not already exist in the database
     *
     * @param event registerEvent received by client
     * @return true(not find), false(already in the db)
     */
    public boolean isValidRegistration(RegisterEvent event){
        // Get users coll
        MongoCollection coll = this.db.getCollection("users");

        // Find all
        FindIterable users = coll.find(new Document());

        for(Object user : users){
            Document doc = (Document) user;
            // if username already exists in the database, refuse
            if(event.getUsername().equals(doc.get("name")))
                return false;
        }

        return true;
    }


    /**
     * Check if user is in database or not
     *
     * @param event loginEvent received by client
     * @return true(find), false(404)
     */
    public boolean isValidUser(LoginEvent event){
        // Get users coll
        MongoCollection coll = this.db.getCollection("users");

        // Find all
        FindIterable users = coll.find(new Document());

        for(Object user : users){
            Document doc = (Document) user;
            // if username && password are correct, accept
            if(event.getUsername().equals(doc.get("name")) && event.getPassword().equals(doc.get("password")))
                return true;
        }

        // Deny access
        return false;
    }
    /**
     * Add a user in database
     *@param name name of the new user
     *@param userName login of the new user
     *@param password password for the new user
     *@param pubKey public key of the new user
     *
     */
    public void addUser(String name, String userName,String password,PublicKey pubKey){
        //MongoCredential credential = MongoCredential.createMongoCRCredential(dbName,userName,password);
        client = new MongoClient((MongoClientURI) Arrays.asList(this.client));

        MongoCollection coll = this.db.getCollection("users");
        BasicDBObject user = new BasicDBObject();
        user.append("nomClient", userName);
        user.append("loginClient", name);
        user.append("passwordClient", password);
        user.append("idClient", pubKey);

        coll.insertOne(user);

    }

    /**
     * Insert a Document into specified collection
     *
     * @param collectionName users
     * @param document new Document("Troll", "troll")
     */
    public void insertDocument(String collectionName, Document document){
        MongoCollection<Document> collection = this.db.getCollection(collectionName);
        collection.insertOne(document);
    }

    public ArrayList<?> read(unice.miage.projetsd.idcoin.database.DatabaseItems item){
        switch(item) {
            case Transactions:
                return new ArrayList<Transaction>();
            case Blocks:
                return new ArrayList<Block>();
            default:
                System.exit(0);

        }
        return null;
    }
}
