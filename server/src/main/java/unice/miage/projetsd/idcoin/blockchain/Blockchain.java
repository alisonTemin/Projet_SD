package unice.miage.projetsd.idcoin.blockchain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Blockchain {

    private final Database blocksDb;
    private final Database transactionsDb;

    private ArrayList<Block> blocks;
    private ArrayList<Transaction> transactions;

    private final int FeePerTransaction = Transaction.getTransactionFee();

    /**
     * Blockchain constructor
     * @param dbName Database name
     */
    @SuppressWarnings("unchecked")
    public Blockchain(String dbName) throws SQLException {
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
        if(this.checkTransaction(tx))
            this.transactions.add(tx);
        else
            throw new Error("Trying to add an invalid transaction");
    }

    /**
     * Add a block
     *
     * A block is added to the block list:


     */
    public void addBlock(Block block){
        if(this.blocks.size() == 0)
            this.blocks.add(block);
        else
            if(this.checkBlock(block, this.blocks.get(this.blocks.size() - 1)))
                this.blocks.add(block);
            else
                throw new Error("Trying to add invalid block");
    }

    /**
     * Check if block is good comparing to previous
     *
     * A block is valid if :
     *
     * - If the block is the last one (previous index + 1);
     * - If previous block is correct (previous hash == block.previousHash);
     * - The hash is correct (calculated block hash == block.hash);
     * - We are at the right turn to the consensus process
     * - All transactions inside the block are valid;
     * - The sum of output transactions are equal to the sum of input transactions + 1 coin (platform fee);
     * @param newBlock new block
     * @param previousBlock previous block
     */
    public Boolean checkBlock(Block newBlock, Block previousBlock){
        if(newBlock == null && this.blocks.size() == 1)
            throw new Error("Trying to check genesis block");

        assert(newBlock != null);

        byte[] blockHash = newBlock.toHash();

        long expected = previousBlock.getIndex().get();
        long newBlockIndex = newBlock.getIndex().get();

        byte[] previousHash = previousBlock.getHash();
        byte[] newHash = newBlock.getHash();

        if (expected != newBlockIndex) { // Check if the block is the last one
            throw new Error("Invalid index, expected : "+ expected + " got : " + newBlockIndex);
        } else if (!Arrays.equals(previousHash, newBlock.getPreviousHash())) { // Check if the previous block is correct
            throw new Error("Invalid previousHash got : " + Arrays.toString(newHash) + " expected : " + Arrays.toString(previousHash));
        } else if (!Arrays.equals(blockHash, newBlock.getHash())) { // Check if the hash is correct
            throw new Error("Invalid hash !");
        } else if (newBlock.getTurn() > previousBlock.getTurn()) { // If we are at the right turn
            throw new Error("Invalid consensus turn");
        }

        long inputAmount = this.FeePerTransaction;
        long outputAmount = this.FeePerTransaction;

        // For each transaction in this block, check if it is valid
        for(Transaction tx : this.transactions){

            for(Input i : tx.getInputs()){
                inputAmount += i.getAmount();
            }

            for(Output i : tx.getOutputs()){
                outputAmount += i.getAmount();
            }

            if(!tx.check())
                throw new Error("Invalid transaction !" + tx.toString());
        }

        Boolean isInputsAmountGreaterOrEqualThanOutputsAmount = inputAmount >= outputAmount;

        if (!isInputsAmountGreaterOrEqualThanOutputsAmount) {
            throw new Error("Invalid balance");
        }

        long consensusTurnCheck = -1;

        // TODO : Here we will need to check the consensus turn
        if(consensusTurnCheck != -1)
            throw new Error("Consensus turn invalid");

        return true;
    }

    /**
     * Check if transaction is valid (in transactionsDb)
     *
     * @param transaction transaction to check
     */
    public boolean checkTransaction(Transaction transaction){
        if(!transaction.check())
            throw new Error("Invalid transaction");

        for(Transaction tx : this.transactions){
            if(transaction.getIndex().equals(tx.getIndex()))
                throw new Error("Transaction already in chain");
        }

        if(this.getUnspentTransactionsForAddress()){
            throw new Error("There is unspent transactions");
        }

        return false;
    }

    /**
     * Check if there is an input for an output
     *
     * @return yes or no
     */
    private Boolean getUnspentTransactionsForAddress() {
        // Create a list of all transactions outputs found for an address (or all).
        ArrayList<Input> inputs = new ArrayList<>();
        ArrayList<Output> outputs = new ArrayList<>();

        for(Transaction tx : this.transactions){
            outputs.addAll(tx.getOutputs());
            inputs.addAll(tx.getInputs());
        }

        return inputs.size() == outputs.size();
    }
}
