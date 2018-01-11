package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe créée pour la méthode fromJson. On récupère des String pour chaque paramètre de la classe, que l'on convertit ensuite dans la classe Transaction.
 */

public class TransactionString {


    private final int id;
    private String hash;
    private final String type;
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    private final int feePerTransaction = 1;


    private TransactionString(int id, String hash, String type, ArrayList<Input> inputs, ArrayList<Output> outputs){
        this.id = id;
        this.hash = hash;
        this.type = type;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    /**
     * Getter Hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Getter Type
     */
    public String getType() {
        return type;
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
    public int getIndex() {
        return id;
    }
}