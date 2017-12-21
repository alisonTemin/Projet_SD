package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;

public class Blockchain {

    private Database blocksDb;
    private Database transactionsDb;

    private ArrayList<Block> blocks;
    private ArrayList<Transaction> transactions;

    /**
     * Blockchain constructor
     * @param dbName Database name
     */
    @SuppressWarnings("unchecked")
    public Blockchain(String dbName){
       this.blocksDb = new Database(dbName, new ArrayList<Block>());
       this.transactionsDb = new Database(dbName, new ArrayList<Transaction>());

       this.blocks = (ArrayList<Block>) this.blocksDb.read(DatabaseItems.Blocks);
       this.transactions = (ArrayList<Transaction>) this.transactionsDb.read(DatabaseItems.Transactions);
    }

    /**
     * Get all blocks
     * @return Blocks list
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Get all transactions
     * @return Transactions list
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Add a transaction
     *
     */
    public void addTransaction(Transaction tx){

    }

    /**
     * Add a block
     *
     * A block is added to the block list:

     - If the block is the last one (previous index + 1);
     - If previous block is correct (previous hash == block.previousHash);
     - The hash is correct (calculated block hash == block.hash);
     - The difficulty level of the proof-of-work challenge is correct (difficulty at blockchain index n < block difficulty);
     - All transactions inside the block are valid;
     - The sum of output transactions are equal the sum of input transactions + 50 coins representing the reward for the block miner;
     - If there is only 1 fee transaction and 1 reward transaction.

     */
    public void addBlock(Block block){
        // TODO : Implement the right way (see up)
        this.blocks.add(block);
    }

    /**
     * Check if block is good comparing to previous
     *
     * @param newBlock new block
     * @param previousBlock previous block
     */
    public void checkBlock(Block newBlock, Block previousBlock){

    }

    /**
     * Check if transaction is valid (in transactionsDb)
     *
     * @param transaction transaction to check
     */
    public boolean checkTransaction(Transaction transaction){


        return false;
    }
}
