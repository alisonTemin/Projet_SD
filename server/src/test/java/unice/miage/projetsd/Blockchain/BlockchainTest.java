package unice.miage.projetsd.Blockchain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import unice.miage.projetsd.idcoin.blockchain.Block;
import unice.miage.projetsd.idcoin.blockchain.Blockchain;
import unice.miage.projetsd.idcoin.blockchain.Transaction;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Unit test for simple App.
 */
public class BlockchainTest
        extends TestCase
{
    private Blockchain fakeChain;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BlockchainTest( String testName )
    {
        super( testName );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.fakeChain = new Blockchain("troll");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BlockchainTest.class );
    }

    /**
     * Check if genesis block could be added
     */
    public void testCouldAddGenesisBlock(){
        Block genesis = Block.genesis();
        this.fakeChain.addBlock(genesis);
        assertTrue(this.fakeChain.getBlocks().size() > 0);
    }

    public void testCouldAddSecondBlock() {
        Block genesis = Block.genesis();

        this.fakeChain.addBlock(genesis);

        long newIndex = genesis.getIndex().incrementAndGet();
        AtomicLong atomicNewIndex = new AtomicLong(newIndex);
        Block two = new Block(atomicNewIndex, genesis.getHash());

        this.fakeChain.addBlock(two);
        assertTrue(this.fakeChain.getBlocks().size() > 1);
    }

}
