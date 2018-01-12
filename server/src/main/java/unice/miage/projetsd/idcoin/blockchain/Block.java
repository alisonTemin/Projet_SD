package unice.miage.projetsd.idcoin.blockchain;

import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A Block
 */
public class Block {
    /**
     * Index of the block
     */
    private final int index;

    /**
     * Previous hash
     */
    private final byte[] previousHash;

    /**
     * Timestamp (when Block enter in system)
     */
    private final Timestamp timestamp;

    /**
     * Hash of the block
     */
    private byte[] hash;

    /**
     * Consensus step
     */
    private int turn;

    /**
     * Transactions linked to this block
     */
    private ArrayList<Transaction> transactions;

    /**
     * Block constructor.
     *
     * @param index        Index
     * @param previousHash Hash of previous block, first is 0, 64bytes so long
     */
    public Block(int index, byte[] previousHash) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.transactions = new ArrayList<>();
    }


    /**
     * Block constructor.
     *
     * @param index        Index of the block
     * @param previousHash Hash of previous block, first is 0, 64bytes so long
     * @param timestamp    Timestamp when block enter in system
     * @param hash         Hash of the block
     * @param turn         Consensus step
     * @param transactions Transactions linked to this block
     */
    public Block(int index, byte[] previousHash, Timestamp timestamp, int turn, ArrayList<Transaction> transactions, byte[] hash) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.hash = hash;
        this.turn = turn;
        this.transactions = transactions;
    }

    /**
     * Calculate Block value to hash
     * uses this.index + this.previousHash + this.timestamp + this.transactions + this.nonce
     * @return int (or hash?)
     */
    public byte[] toHash() {
        try {
            this.hash = CryptoHelper.hash(this.index + Arrays.toString(this.previousHash) + this.timestamp + this.transactions + this.turn);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return this.hash;
    }

    /**
     * Retrieve the first block of the chain
     * https://en.bitcoin.it/wiki/Genesis_block
     * @return Block
     */
    public static Block genesis() {
       return new Block(1, null);
    }

    @Override
    public String toString() {
        return "{" +
                "\"index\"=" + index +
                ", \"previousHash\"=" + Arrays.toString(previousHash) +
                ", \"timestamp\"=" + timestamp +
                ", \"hash\"=" + Arrays.toString(hash) +
                ", \"turn\"=" + turn +
                ", \"transactions\"=" + transactions +
                '}';
    }

    /**
     * Retrieve a Block from JSON
     * @param json Block as json
     * @return Block
     */
    public static Block fromJson(String json) {

        Gson gson = new Gson();
        BlockString blockstring = gson.fromJson(json, BlockString.class);

        byte[] previousHash = blockstring.getPreviousHash().getBytes();
        Timestamp timestamp = new Timestamp(Long.parseLong(blockstring.getTimestamp()));
        byte[] hash = blockstring.getHash().getBytes();
        int turn = blockstring.getTurn();
        ArrayList<Transaction> transactions = blockstring.getTransactions();

        Block block = new Block(blockstring.getIndex(),previousHash,timestamp,turn, transactions, hash);

        //Block block = new Block(index,previousHash,timestamp, hash, turn, transactions);
        /*
        // Typical Block in JSON
        { // Block
            "index": 0, // (first block: 0)
            "previousHash": "0", // (hash of previous block, first block is 0) (64 bytes)
            "timestamp": 1465154705, // number of seconds since January 1, 1970
            "turn": 0, // turn used to identify the consensus step.
            "transactions": [ // list of transactions inside the block

            ],
            "hash": "c4e0b8df46...199754d1ed" // hash taken from the contents of the block: sha256 (index + previousHash + timestamp + nonce + transactions) (64 bytes)
        }
         */
        return block;
    }

    /**
     * Getter Index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Getter Hash
     */
    public byte[] getHash() {
        if (this.hash == null)
            this.toHash();

        return this.hash;
    }

    /**
     * Getter Turn
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Getter PreviousHash
     */
    public byte[] getPreviousHash() {
        return previousHash;
    }
}
