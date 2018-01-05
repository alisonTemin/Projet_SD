package unice.miage.projetsd.idcoin.blockchain;

import com.google.gson.Gson;

import java.sql.Timestamp;
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

    public BlockString(AtomicLong index, String previousHash, String timestamp) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    private ArrayList<Transaction> transactions;

    /**
     * Retrieve a Block from JSON
     * @param data Block as json
     * @return Block
     */
    public static BlockString fromJson(String data) {
        Gson gson = new Gson();
        BlockString block = gson.fromJson(data, BlockString.class);
        return block;
    }


    public AtomicLong getIndex() {
        return this.index;
    }



    public int getTurn() {
        return this.turn;
    }
}
