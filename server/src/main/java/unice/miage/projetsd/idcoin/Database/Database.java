package unice.miage.projetsd.idcoin.database;

import java.sql.*;

public class Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/SD";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

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
    }

    private void insertObject(String name, String price){
        try {
            this.statement = this.connection.createStatement();
            String sql = "INSERT INTO objects VALUES ('" + name + "', '"+ price + "')";
            this.statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSell(String seller, String objectId){
        try {
            this.statement = this.connection.createStatement();
            String sql = "INSERT INTO sells VALUES ('" + seller + "', "+ objectId + ")";
            this.statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}