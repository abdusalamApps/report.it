package report.it;

import java.sql.*;

/**
 * A class to handle connecting to
 * the database and presenting a connection object
 * to other classes.
 * @author Abdusalam Yabrak
 * @version 1.0
 */

public class Database {

    private static String databaseServerAddress = "35.228.254.209";
    private static String databaseUser = "abdo"; // database login user
    private static String databasePassword = ""; // database login password
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
