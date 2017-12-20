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
     */
    public void addTransaction(){

    }

    /**
     * Add a block
     */
    public void addBlock(){

    }

    /**
     * Check if block is good comparing to previous
     * @param newBlock new block
     * @param previousBlock previous block
     */
    public void checkBlock(Block newBlock, Block previousBlock){

    }

    /**
     * Check if transaction is valid (in transactionsDb)
     * @param transaction transaction to check
     */
    public boolean checkTransaction(Transaction transaction){
        // TODO : implement
        return false;
    }
}
