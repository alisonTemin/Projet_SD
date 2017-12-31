package unice.miage.projetsd.idcoin.database;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Transaction;
import unice.miage.projetsd.idcoin.events.LoginEvent;

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
    private MongoClientURI uri;

    /**
     * Instance collections
     */
    private ArrayList<MongoCollection<Document>> collections;

    /**
     * database constructor.
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

    public void insertDocument(String collection, Document doc){
        MongoCollection<Document> table = this.db.getCollection(collection);
        table.insertOne(doc);
    }

    public ArrayList<?> read(DatabaseItems item){
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

    public ArrayList<MongoCollection<Document>> getCollections() {
        return collections;
    }

    public boolean isValidUser(LoginEvent event){
        MongoCollection coll = this.db.getCollection("users");

        FindIterable users = coll.find(new Document());

        for(Object user : users){
            Document doc = (Document) user;
            if(event.getUsername().equals(doc.get("name")) && event.getPassword().equals(doc.get("password")))
                return true;
        }

        return false;
    }
}
