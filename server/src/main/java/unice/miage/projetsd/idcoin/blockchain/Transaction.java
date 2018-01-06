package unice.miage.projetsd.idcoin.blockchain;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction {

    /**
     * ID of the transaction
     */
    private final AtomicLong id;
    /**
     * Hash
     */
    private byte[] hash;

    /**
     * Transaction type
     */
    private final String type;

    /**
     * Inputs
     */
    private ArrayList<Input> inputs;

    /**
     * Ouputs
     */
    private ArrayList<Output> outputs;

    /**
     * Fee per transaction
     */
    private final int feePerTransaction = 1;

    /**
     * Transaction constructor.
     *
     * @param id        Id of the transaction
     * @param hash      Hash of the transaction
     * @param type      Type of the transaction
     */
    public Transaction(AtomicLong id, byte[] hash, String type){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

    /**
     * Transaction constructor.
     *
     * @param id        Id of the transaction
     * @param hash      Hash of the transaction
     * @param type      Type of the transaction
     * @param inputs    Inputs
     * @param outputs   Outputs
     */
    private Transaction(AtomicLong id, byte[] hash, String type, ArrayList<Input> inputs, ArrayList<Output> outputs){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    /**
     * Encode a transaction to hash
     * @uses CryptoHelper to hash
     * @return Transaction has an hash
     */
    public byte[] toHash(){
        try {
            this.hash = CryptoHelper.hash(this.id + this.type + this.inputs.toString() + this.outputs.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return this.hash;
    }

    /**
     * Check if a transaction is valid following these steps :
     *
     *  - If the transaction hash is correct (calculated transaction hash == transaction.hash);
     *  - The signature of all input transactions are correct (transaction data is signed by the public key of the address);
     *  - The sum of input transactions are greater than output transactions, it needs to leave some room for the transaction fee;
     *  - If the transaction isn't already in the blockchain
     *  - If all input transactions are unspent in the blockchain
     *
     * @return Valid or not
     */
    public boolean check(){
        // Check if the transaction hash is correct
        Boolean isTransactionValid = Arrays.equals(this.toHash(), this.hash);

        Boolean isValidSignature = false;
        Boolean isSumInputGreater = false;
        Boolean isEnoughFee = false;

        if(!isTransactionValid)
            throw new Error("Invalid transaction hash");

        // Check if the signature of all input transactions are correct (transaction data is signed by the public key of the address)
        for (Input current : this.inputs) {
            isValidSignature = CryptoHelper.verifySignature(current.getAddress(), current.getSignature(), current.getPreviousTxHash());
            if (!isValidSignature)
                throw new Error("Invalid signature for input" + current.toString());
        }

        if (this.type.equals("bid")){
            long inputAmount = 0;
            long outputAmount = 0;

            for(Input i : this.inputs){
                inputAmount += i.getAmount();
            }

            for(Output i : this.outputs){
                outputAmount += i.getAmount();
            }

            isSumInputGreater = inputAmount > outputAmount;
            isEnoughFee = (inputAmount - outputAmount) >= feePerTransaction; // 1 because the fee is 1 satoshi per transaction
        }

        return isValidSignature && isSumInputGreater && isEnoughFee;
    }

    /**
     * Retrieve a Transaction from JSON
     * @param json Transaction as json
     * @return Transaction
     */
    public static Transaction fromJSON(String json){

        Gson gson = new Gson();
        TransactionString transactionString = gson.fromJson(json, TransactionString.class);

        AtomicLong id = transactionString.getIndex();
        byte[] hash = transactionString.getHash().getBytes();
        String type = transactionString.getType();
        ArrayList<Input> inputs = transactionString.getInputs();
        ArrayList<Output> outputs = transactionString.getOutputs();

        return new Transaction(id, hash, type, inputs, outputs);
    }


    public void addInput(Input input){
        this.inputs.add(input);
    }

    /**
     * Getter Inputs
     */
    public ArrayList<Input> getInputs() {
        return inputs;
    }

    /**
     * Getter Outputs
     */
    public ArrayList<Output> getOutputs() {
        return outputs;
    }

    /**
     * Getter TransactionFee
     */
    public static int getTransactionFee(){
        return 1;
    }

    /**
     * Getter Id
     */
    public AtomicLong getIndex() {
        return id;
    }
}