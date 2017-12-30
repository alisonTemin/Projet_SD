package unice.miage.projetsd.idcoin.Database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Transaction;

public class Database{

    private MongoClient client;
    private MongoDatabase db;
    private String[] collectionsNames = new String[]{"client","objet","enchere", "enchereTerminee"};
    private ArrayList<MongoCollection<Document>> collections;

    public Database(String dbName, ArrayList<?> blocks) throws SQLException {
        this.client = new MongoClient(new MongoClientURI("mongodb://rootSD:rootSD06!@ds163016.mlab.com:63016/biddata"));
        this.db = this.client.getDatabase("biddb");
    }

    /*connection to local database using mongoDB*/
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

}
