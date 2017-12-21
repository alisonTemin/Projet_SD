package server.src.main.java.unice.miage.projetsd.idcoin.blockchain;

import java.security.PublicKey;

public class Output {

    private PublicKey address;
    private long amount;

    public PublicKey getAddress() {
        return address;
    }

    public long getAmount() {
        return amount;
    }
}
