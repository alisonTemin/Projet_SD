package server.src.main.java.unice.miage.projetsd.idcoin.blockchain;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Database{

    public Database(String dbName, ArrayList<?> blocks) {
        // TODO : Implement
    }

    /*connection to local database using mongoDB*/
    public void connectionToDb() {

        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = (DB) mongoClient.getDatabase("biddb");
            List<String> dbs = (List<String>) mongo.listDatabaseNames();

            for (String dab : dbs) {
                System.out.println(db);
            }
            DB datab = (DB) mongo.getDatabase("biddb");
            Set<String> tables = db.getCollectionNames();

            for (String coll : tables) {
                System.out.println(coll);
            }

            System.out.println("Done");

            //DBCollection table = datab.getCollection("client");
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

    public static void main( String[] args ) {
        String nameDB = "biddb";
        ArrayList<?> theList = new ArrayList<>();
        Database mydb = new Database(nameDB, theList);
        mydb.connectionToDb();
    }
}
