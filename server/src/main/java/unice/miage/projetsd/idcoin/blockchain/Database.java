package unice.miage.projetsd.idcoin.blockchain;

import java.util.ArrayList;

import static javafx.application.Platform.exit;

public class Database {

    public Database(String dbName, ArrayList<?> blocks) {
        // TODO : Implement
    }

    public ArrayList<?> read(DatabaseItems item){
        switch(item) {
            case Transactions:
                return new ArrayList<Transaction>();
            case Blocks:
                return new ArrayList<Block>();
            default:
                exit();

        }
        return null;
    }
}
