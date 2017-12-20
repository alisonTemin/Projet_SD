package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;

/**
 * A Block
 */
class Block {
    private int index;
    private long previousHash;
    private int timestamp;
    private int nonce;
    private long hash;

    private ArrayList<Transaction> transactions;

    /**
     *
     * @param index Index
     * @param previousHash Hash of previous block, first is 0, 64bytes so long
     * @param timestamp
     * @param nonce nonce is used to define proof of work step
     */
    Block(int index, long previousHash, int timestamp, int nonce){
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }

    /**
     * Calculate Block value to hash
     * @return int (or hash?)
     */
    public int toHash() {
        this.hash = 0;
         // INFO: Usually there are different implementations of the hash algorithm, for example: https://en.bitcoin.it/wiki/Hashcash
        //return CryptoUtil.hash(this.index + this.previousHash + this.timestamp + JSON.stringify(this.transactions) + this.nonce);
        return 0;
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
       // TODO : First block of the chain, need to generate example in JS below
       // explain : https://en.bitcoin.it/wiki/Genesis_block
       return null;
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
            "nonce": 0, // nonce used to identify the proof-of-work step.
            "transactions": [ // list of transactions inside the block

            ],
            "hash": "c4e0b8df46...199754d1ed" // hash taken from the contents of the block: sha256 (index + previousHash + timestamp + nonce + transactions) (64 bytes)
        }
         */
        return null;
    }
}
