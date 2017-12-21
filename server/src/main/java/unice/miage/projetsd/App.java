package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Block;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Launching the money, and everything related
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Blockchain blockchain = new Blockchain("troll");
        Block genesis = Block.genesis();
        blockchain.addBlock(genesis);

        long newIndex = genesis.getIndex().incrementAndGet();
        AtomicLong atomicNewIndex = new AtomicLong(newIndex);
        Block two = new Block(atomicNewIndex, genesis.getHash());
        System.out.println( "Blockchain started : " + blockchain.checkBlock(two, genesis) );
    }
}
