package server.src.main.java.unice.miage.projetsd.idcoin.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Random;

public class CryptoHelper {

    /**
     * Calculate hash, digest contains the hashed string and hex contains an hexadecimal ASCII string padded
     * @param toHash String to Hash
     * @return String hashed
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is required but not integrated
     */
    public static byte[] hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance( "SHA-256" );

        md.update( toHash.getBytes( StandardCharsets.UTF_8 ) );

        return md.digest();
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


    public static boolean verifySignature(PublicKey pubKey, byte[] message, byte[] signature) {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert sig != null;
            sig.initVerify(pubKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            sig.update(message);
            return sig.verify(signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }



}
