package unice.miage.projetsd.idcoin.blockchain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CryptoHelper {

    /**
     * Calculate hash, digest contains the hashed string and hex contains an hexadecimal ASCII string padded
     * @param toHash String to Hash
     * @return String hashed
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is required but not integrated
     */
    public static String hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );

        md.update( toHash.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        AtomicLong id = new AtomicLong();

        return String.format( "%064x", new BigInteger( 1, digest ) );
    }

    /**
     * Generate a random hex string of 64 bytes
     * @return random hex
     */
    public static String generateRandomId(){
        int numChars = 64;
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < numChars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numChars);
    }
}
