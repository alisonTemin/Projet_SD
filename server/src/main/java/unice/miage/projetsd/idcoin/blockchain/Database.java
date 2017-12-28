package unice.miage.projetsd.idcoin.blockchain;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.connection.Connection;
import org.bson.Document;

import javax.xml.crypto.Data;

public class Database{

    public Database(String dbName, ArrayList<?> blocks) throws SQLException {
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

    public void insertDocument(MongoCollection collection, Document doc){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://rootSD:rootSD06!@ds163016.mlab.com:63016/biddata"));
        String nameCollection = "";
        MongoDatabase database = mongoClient.getDatabase("biddb");
        MongoCollection table = database.getCollection(nameCollection);
        BasicDBObject document = new BasicDBObject();
        table.insertOne(document);
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
