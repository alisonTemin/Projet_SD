package unice.miage.projetsd.idcoin.blockchain;

import java.security.PublicKey;
import java.util.concurrent.atomic.AtomicLong;

public class Input {

    /**
     * Input constructor.
     * @param previousTxHash
     * @param index
     * @param amount
     */
    public Input(byte[] previousTxHash, AtomicLong index, PublicKey address, long amount){
        this.address = address;
        this.previousTxHash = previousTxHash;
        this.index = index;
        this.amount = amount;
    }
    /**
     * transaction hash taken from a previous unspent transaction output (64 bytes)
     */
    private byte[] previousTxHash;
    /**
     * index of the transaction taken from a previous unspent transaction output
     */
    private AtomicLong index;
    /**
     * Amount engaged
     */
    private long amount;
    /**
     * from address (64 bytes)
     */
    private PublicKey address;
    /**
     * transaction input hash: sha256 (transaction + index + amount + address) signed with owner address's secret key (128 bytes)
     */
    private byte[] signature;

    public byte[] getPreviousTxHash() {
        return this.previousTxHash;
    }

    public AtomicLong getIndex() {
        return index;
    }

    public long getAmount() {
        return amount;
    }

    public PublicKey getAddress() {
        return address;
    }

    public byte[] getSignature() {
        return signature;
    }
}
