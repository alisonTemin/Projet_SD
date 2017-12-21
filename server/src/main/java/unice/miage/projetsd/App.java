package server.src.main.java.unice.miage.projetsd;

import server.src.main.java.unice.miage.projetsd.idcoin.blockchain.Blockchain;
import server.src.main.java.unice.miage.projetsd.idcoin.blockchain.Block;
import server.src.main.java.unice.miage.projetsd.idcoin.blockchain.Transaction;
import server.src.main.java.unice.miage.projetsd.idcoin.blockchain.Database;

import java.util.ArrayList;
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
        //blockchain.addTransaction(tx);
        System.out.println( "Blockchain started : " + blockchain.checkBlock(two, genesis) );



        // TODO : Start socket server
        // TODO : Set listeners
        // TODO : Add console log wrapper
    }
}
