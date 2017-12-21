package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Block;

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

        System.out.println( "Blockchain started" );
    }
}
