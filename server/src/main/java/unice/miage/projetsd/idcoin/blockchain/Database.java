package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import javax.xml.crypto.Data;

public class Database{

    public Database(String dbName, ArrayList<?> blocks) {
        // TODO : Implement
    }

    /*connection to local database using mongoDB*/
    public void importDb() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://rootSD:rootSD06!@ds163016.mlab.com:63016/biddata"));

        String name = "client";
            System.out.print("connection : ok \n");
        MongoIterable<String> db =  mongoClient.listDatabaseNames();
        MongoDatabase database = mongoClient.getDatabase("biddb");
        MongoCollection<Document> collClient = database.getCollection("client");
        MongoCollection<Document> collObjet = database.getCollection("objet");
        MongoCollection<Document> collEnchere = database.getCollection("enchere");
        MongoCollection<Document> collEnchereTerminee = database.getCollection("enchereTerminee");
        System.out.println("Import : Done");
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
