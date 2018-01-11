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
    private Statement statement;

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

    public int insertObject(String name, String price){
        try {
            this.statement = this.connection.createStatement();
            String sql = "INSERT INTO objects VALUES ('" + name + "', '"+ price + "')";
            return this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean insertSell(String seller, int objectId){
        try {
            this.statement = this.connection.createStatement();
            String sql = "INSERT INTO sells VALUES ('" + seller + "', "+ objectId + ")";
            return this.statement.execute(sql);
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