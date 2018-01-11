package unice.miage.projetsd.idcoin.database;

import java.security.PublicKey;
import java.util.ArrayList;

import unice.miage.projetsd.idcoin.events.LoginEvent;
import unice.miage.projetsd.idcoin.events.RegisterEvent;

public class Database{
    /**
     * Every usable collection name
     */
    private ArrayList<Object> bids;

    /**
     * database constructor.
     *
     * @param dbName name
     */
    public Database(String dbName) {
        this.bids = new ArrayList<>();
    }

    /**
     * Add every collections to current instance
     */
    public void importDb() {
    }

    public void addBid(Object bid){
        this.bids.add(bid);
    }

    /**
     * Check if user does not already exist in the database
     *
     * @param event registerEvent received by client
     * @return true(not find), false(already in the db)
     */
    public boolean isValidRegistration(RegisterEvent event){
        return true;
    }


    /**
     * Check if user is in database or not
     *
     * @param event loginEvent received by client
     * @return true(find), false(404)
     */
    public boolean isValidUser(LoginEvent event){
        return true;
    }
    /**
     * Add a user in database
     *@param name name of the new user
     *@param userName login of the new user
     *@param password password for the new user
     *@param pubKey public key of the new user
     *
     */
    public void addUser(String name, String userName,String password,PublicKey pubKey){
    }

    /**
     * Insert a Document into specified collection
     *
     * @param collectionName users
     */
    public void insertDocument(String collectionName){
    }

    public ArrayList<Object> getBids() {
        return bids;
    }
}
