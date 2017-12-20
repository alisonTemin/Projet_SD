package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;

public class Database {

    Database(String dbName, ArrayList<?> blocks) {
        // TODO : Implement
    }

    public ArrayList<?> read(DatabaseItems item){
        switch(item) {
            case Transactions:
                return new ArrayList<Transaction>();
            case Blocks:
                return new ArrayList<Block>();
            default:
                System.exit(0);

        }
        return null;
    }
}
