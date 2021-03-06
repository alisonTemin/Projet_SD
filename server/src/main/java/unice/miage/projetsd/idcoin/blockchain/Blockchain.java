package unice.miage.projetsd.idcoin.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;

public class Blockchain {

    private ArrayList<Block> blocks;
    private ArrayList<Transaction> transactions;
    private int difficulty = 2;

    private final int FeePerTransaction = Transaction.getTransactionFee();

    /**
     * Blockchain constructor
     */
    @SuppressWarnings("unchecked")
    public Blockchain() {
       this.blocks = new ArrayList<>();
       this.transactions = new ArrayList<>();
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
     * A block is added to the block list
     */
    public void addBlock(Block block) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(this.blocks.size() == 0){
            System.out.println("Adding one block to the chain");
            this.blocks.add(block);
        } else
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
    public Boolean checkBlock(Block newBlock, Block previousBlock) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(newBlock == null && this.blocks.size() == 1)
            throw new Error("Trying to check genesis block");

        assert(newBlock != null);

        byte[] blockHash = newBlock.toHash();

        long expected = previousBlock.getIndex();
        long newBlockIndex = newBlock.getIndex();

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

        return true;
    }

    /**
     * Check if transaction is valid (in transactionsDb)
     *
     * @param transaction transaction to check
     */
    private boolean checkTransaction(Transaction transaction){
        if(!transaction.check())
            throw new Error("Invalid transaction");

        for(Transaction tx : this.transactions){
            if(transaction.getIndex() == tx.getIndex())
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

    private boolean checkPOW(String proof, String message) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	boolean check;
    	int nbZero = proof.length();
    	byte[] hashMessage;
    	String hashFinal;

    	int nb = 0;
    	do{
    		hashMessage = CryptoHelper.hash(message+nb);

    		hashFinal = new String (hashMessage,"UTF-8");

    		if(hashFinal.substring(0,nbZero).equals(proof))
    			check = true;
    		else
    			check = false;

    		nb++;

    	} while (!check);


    	return check;
    }
    
    
}
