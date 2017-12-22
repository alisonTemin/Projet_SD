package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Input;
import unice.miage.projetsd.idcoin.blockchain.Transaction;
import unice.miage.projetsd.idcoin.blockchain.Database;

import java.util.ArrayList;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Launching the money, and everything related
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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



        // TODO : Start socket server
        // TODO : Set listeners
        // TODO : Add console log wrapper

        String nameDB = "biddb";
        ArrayList<?> theList = new ArrayList<>();
        Database mydb = new Database(nameDB, theList);
        mydb.importDb();
    }
}
