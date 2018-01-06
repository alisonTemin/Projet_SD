package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe créée pour la méthode fromJson. On récupère des String pour chaque paramètre de la classe, que l'on convertit ensuite dans la classe Block.
 */
public class BlockString {

    private final AtomicLong index;
    private final String previousHash;
    private final String timestamp;
    private String hash;
    private int turn;
    private ArrayList<Transaction> transactions;

    public BlockString(AtomicLong index, String previousHash, String timestamp) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
    }

    /**
     * Getter PreviousHash
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * Getter Timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Getter Hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Getter Transactions
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Getter Index
     */
    public AtomicLong getIndex() {
        return this.index;
    }

    /**
     * Getter Turn
     */
    public int getTurn() {
        return this.turn;
    }
}
