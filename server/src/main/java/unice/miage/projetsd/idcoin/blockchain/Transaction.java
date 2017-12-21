package unice.miage.projetsd.idcoin.blockchain;

import com.sun.istack.internal.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
class Transaction {

    private AtomicLong id;
    private byte[] hash;
    private String type;
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private int feePerTransaction = 1;

    Transaction(AtomicLong id, byte[] hash, String type){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
    }

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
            return CryptoHelper.hash(this.id + this.type + this.inputs.toString() + this.outputs.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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

        if (this.type.equals("regular")){
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

    public static Transaction fromJSON(String json){
        // TODO : Implement
        return new Transaction(new AtomicLong(0), new byte[2], "Troll");
    }
}