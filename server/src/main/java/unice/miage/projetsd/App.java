package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.*;
import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.ws.Socket;

import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Launching the money, and everything related
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Init a new blockchain
        blockchainCeremony();

        // Setup database in memory, start socketIO instance
        setupDatabaseAndStart();


        //Test de la méthode fromJson de Block
        /*String data = "{ \"index\": 0, \"previousHash\": \"0\", \"timestamp\": 1465154705, \"turn\": 0, \"transactions\": [], \"hash\": \"c4e0b8df46199754d1ed\" }";

        Block block = Block.fromJson(data);
        System.out.println(block);*/


    }

    private static void blockchainCeremony(){
        // Blockchain testing
        Blockchain blockchain = new Blockchain("troll");

        // The genesis is the first block of the chain ^^
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

    private static void setupDatabaseAndStart(){
        String nameDB = "biddata";
        Database biddata = new Database(nameDB);

        assert biddata != null;
        biddata.importDb();

        Socket socketIO = new Socket("127.0.0.1", 9002);
        socketIO.init();
        socketIO.setListeners();
        socketIO.start();
        socketIO.setDb(biddata);
    }
}
