package unice.miage.projetsd.idcoin.database;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/SD";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    private ArrayList<Object> bids;

    private Connection connection;
    private PreparedStatement statement;

    public Database() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.bids = new ArrayList<>();
    }

    public long insertObject(String name, int price){
        try {
            this.statement = this.connection.prepareStatement("INSERT INTO objects (name, price) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            this.statement.setString(1, name);
            this.statement.setInt(2, price);
            this.statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean insertSell(String seller, long objectId){
        try {
            this.statement = this.connection.prepareStatement("INSERT INTO sells (seller, objectId) VALUES (?,?)");
            this.statement.setString(1, seller);
            this.statement.setLong(2, objectId);
            return this.statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public void addBid(Object bid){
        this.bids.add(bid);
    }

    public ArrayList<Object> getBids() {
        return bids;
    }
}