package unice.miage.projetsd.idcoin.blockchain;

/*
// Typical Block in JSON
{ // Block
    "index": 0, // (first block: 0)
    "previousHash": "0", // (hash of previous block, first block is 0) (64 bytes)
    "timestamp": 1465154705, // number of seconds since January 1, 1970
    "nonce": 0, // nonce used to identify the proof-of-work step.
    "transactions": [ // list of transactions inside the block
        { // transaction 0
            "id": "63ec3ac02f...8d5ebc6dba", // random id (64 bytes)
            "hash": "563b8aa350...3eecfbd26b", // hash taken from the contents of the transaction: sha256 (id + data) (64 bytes)
            "type": "regular", // transaction type (regular, fee, reward)
            "data": {
                "inputs": [], // list of input transactions
                "outputs": [] // list of output transactions
            }
        }
    ],
    "hash": "c4e0b8df46...199754d1ed" // hash taken from the contents of the block: sha256 (index + previousHash + timestamp + nonce + transactions) (64 bytes)
}
*/

public class Block {
        public int toHash() {
            // INFO: Usually there are different implementations of the hash algorithm, for example: https://en.bitcoin.it/wiki/Hashcash
            //return CryptoUtil.hash(this.index + this.previousHash + this.timestamp + JSON.stringify(this.transactions) + this.nonce);
            return 0;
        }

        public int getDifficulty() {
            // 14 is the maximum precision length supported
            return 16;
        }

        public static int genesis() {
            // TODO : First block of the chain, need to generate example in JS below
            // explain : https://en.bitcoin.it/wiki/Genesis_block
            // The genesis block is fixed
            /*let genesisBlock = Block.fromJson({
                    index: 0,
                    previousHash: '0',
                    timestamp: 1465154705,
                    nonce: 0,
                    transactions: [
            Transaction.fromJson({
                    id: '63ec3ac02f822450039df13ddf7c3c0f19bab4acd4dc928c62fcd78d5ebc6dba',
                    hash: null,
                    type: 'regular',
                    data: {
                inputs: [],
                outputs: []
            }
                })
                ]
            });

            return genesisBlock;*/
            return 0;
        }

    /**
     * Retrieve a Block from JSON
     * @param data Block as json
     * @return Block
     */
    public static Block fromJson(String data) {
            /*let block = new Block();
            R.forEachObjIndexed((value, key) => {
                if (key == 'transactions' && value) {
                    block[key] = Transactions.fromJson(value);
                } else {
                    block[key] = value;
                }
            }, data);

            block.hash = block.toHash();
            return block;*/
            return new Block();
    }
}
