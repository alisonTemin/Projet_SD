package unice.miage.projetsd;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Blockchain;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Blockchain blockchain = new Blockchain();
        Block genesis = Block.genesis();
        blockchain.addBlock(genesis);
        assertTrue(blockchain.getBlocks().size() > 0);
    }
}
