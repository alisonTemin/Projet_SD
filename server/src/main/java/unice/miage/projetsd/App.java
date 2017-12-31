package unice.miage.projetsd;

import com.mongodb.client.MongoCollection;
import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Input;
import unice.miage.projetsd.idcoin.blockchain.Transaction;
import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.ws.Socket;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Launching the money, and everything related
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        blockchainCeremony();
        setupDatabase();
        Socket socketIO = new Socket("127.0.0.1", 9002);
        socketIO.init();
        socketIO.setListeners();
        socketIO.start();

        // TODO : Add console log wrapper
    }

    private static void blockchainCeremony(){
        // Blockchain testing
        Blockchain blockchain = new Blockchain("troll");
        Block genesis = Block.genesis();
        blockchain.addBlock(genesis);

        long newIndex = genesis.getIndex().incrementAndGet();
        AtomicLong atomicNewIndex = new AtomicLong(newIndex);
        Block two = new Block(atomicNewIndex, genesis.getHash());

        Transaction tx = new Transaction(new AtomicLong(0), two.getHash(), "bid");

        // TODO : Key generator

        Input i = new Input(tx.toHash(), new AtomicLong(1), null, 200);
        tx.addInput(i);

        //blockchain.addTransaction(tx);
        System.out.println( "Blockchain started : " + blockchain.checkBlock(two, genesis) );
    }

    private static void setupDatabase(){
        String nameDB = "biddb";
        Database mydb = new Database(nameDB);

        assert mydb != null;
        mydb.importDb();
        MongoCollection coll = mydb.getDb().getCollection("users");
        //Object first = coll.find().first();
        //System.out.println(first);
    }
}
