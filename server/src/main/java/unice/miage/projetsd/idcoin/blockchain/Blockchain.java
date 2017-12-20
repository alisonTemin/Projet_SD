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

    public void addTransaction(){

    }

    public void addBlock(){

    }

    public void checkBlock(Block newBlock, Block previousBlock){

    }

    public void checkTransaction(Transaction transaction){

    }
}
