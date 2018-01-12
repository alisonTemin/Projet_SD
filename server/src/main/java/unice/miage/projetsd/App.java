package unice.miage.projetsd;

import unice.miage.projetsd.idcoin.blockchain.*;
import unice.miage.projetsd.idcoin.database.Database;
import unice.miage.projetsd.idcoin.ws.Socket;

import java.io.UnsupportedEncodingException;
import java.security.*;

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
    }

    private static void blockchainCeremony() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Blockchain testing
        Blockchain blockchain = new Blockchain();

        // The genesis is the first block of the chain ^^
        Block genesis = Block.genesis();
        blockchain.addBlock(genesis);

        Block two = new Block(blockchain.getBlocks().size(), genesis.getHash());

        System.out.println( "Blockchain started : " + blockchain.checkBlock(two, genesis) );

        // Setup database in memory, start socketIO instance
        setupDatabaseAndStart(blockchain);
    }

    private static void setupDatabaseAndStart(Blockchain blockchain){
        Database biddata = new Database();

        assert biddata != null;

        Socket socketIO = new Socket("127.0.0.1", 9002, blockchain);
        socketIO.init();
        socketIO.setListeners();
        socketIO.start();
        socketIO.setDb(biddata);
    }
}
