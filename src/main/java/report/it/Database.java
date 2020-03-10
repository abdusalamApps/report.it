package report.it;

import java.sql.*;

public class Database {

    private static String databaseServerAddress = "localhost";
    private static String databaseUser = "root"; // database login user
    private static String databasePassword = "rio78asj"; // database login password
    private static String database = "reportit"; // the database to use, i.e.

    protected Connection conn = null;

    public Database() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + databaseServerAddress + "/" + database,
                    databaseUser,
                    databasePassword);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
