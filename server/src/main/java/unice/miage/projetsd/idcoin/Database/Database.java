package unice.miage.projetsd.idcoin.Database;

import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Transaction;

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
    private String[] collectionsNames = new String[]{"client","objet","enchere", "enchereTerminee"};

    /**
     * Connection uri on Mlab
     */
    private String uri = "mongodb://rootSD:rootSD06!@ds163016.mlab.com:63016/biddata";

    /**
     * Instance collections
     */
    private ArrayList<MongoCollection<Document>> collections;

    /**
     * Database constructor.
     * @param dbName name
     */
    public Database(String dbName) {
        this.client = new MongoClient(new MongoClientURI(this.uri));
        this.db = this.client.getDatabase(dbName);
        this.collections = new ArrayList<>();
    }

    /**
     * Add every collections to current instance
     */
    public void importDb() {
        for(String coll : collectionsNames){
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
}
