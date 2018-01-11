package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.*;
import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.ws.Socket;

import java.io.UnsupportedEncodingException;
import java.security.*;
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
        try {
            blockchainCeremony();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Setup database in memory, start socketIO instance



    }

    private static void blockchainCeremony() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Blockchain testing
        Blockchain blockchain = new Blockchain("troll");

        // The genesis is the first block of the chain ^^
        Block genesis = Block.genesis();
        blockchain.addBlock(genesis);

        long newIndex = genesis.getIndex().incrementAndGet();
        AtomicLong atomicNewIndex = new AtomicLong(newIndex);
        Block two = new Block(atomicNewIndex, genesis.getHash());

        Transaction tx = new Transaction(0, two.getHash(), "bid");


        Input i = new Input(tx.toHash(), 1, null, 200);
        tx.addInput(i);

        //blockchain.addTransaction(tx);
        System.out.println( "Blockchain started : " + blockchain.checkBlock(two, genesis) );
        setupDatabaseAndStart(blockchain);
    }

    private static void setupDatabaseAndStart(Blockchain blockchain){
        String nameDB = "biddata";
        Database biddata = new Database(nameDB);

        assert biddata != null;
        biddata.importDb();

        Socket socketIO = new Socket("127.0.0.1", 9002, blockchain);
        socketIO.init();
        socketIO.setListeners();
        socketIO.start();
        socketIO.setDb(biddata);
    }
}
