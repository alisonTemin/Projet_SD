package unice.miage.projetsd.idcoin.blockchain;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A Block
 */
public class Block {
    private AtomicLong index;
    private byte[] previousHash;
    private Timestamp timestamp;
    private byte[] hash;
    private int turn;

    private ArrayList<Transaction> transactions;

    /**
     *
     * @param index Index
     * @param previousHash Hash of previous block, first is 0, 64bytes so long
     */
    Block(AtomicLong index, byte[] previousHash){
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Calculate Block value to hash
     * uses this.index + this.previousHash + this.timestamp + this.transactions + this.nonce
     * @return int (or hash?)
     */
    public byte[] toHash() {
        try {
            this.hash = CryptoHelper.hash(this.index.toString() + Arrays.toString(this.previousHash) + this.timestamp + this.transactions + this.turn);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return this.hash;
    }

    public int getDifficulty() {
        // TODO : Ask for how to handle that in Java
       return 16;
    }

    /**
     * Retrieve the first block of the chain
     * https://en.bitcoin.it/wiki/Genesis_block
     * @return Block
     */
    public static Block genesis() {
       return new Block(new AtomicLong(0), null);
    }

    /**
     * Retrieve a Block from JSON
     * @param data Block as json
     * @return Block
     */
    public static Block fromJson(String data) {
        // TODO : Implement

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
        return null;
    }


}
