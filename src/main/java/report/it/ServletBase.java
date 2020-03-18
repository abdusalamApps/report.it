package report.it;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * This class is the superclass for all servlets in the application.
 * It includes basic functionality required by many servlets, like for example a page head
 * written by all servlets, and the connection to the database.
 * <p>
 * This application requires a database.
 * For username and password, see the constructor in this class.
 *
 * <p>The database can be created with the following SQL command:
 * mysql> create database base;
 * <p>The required table can be created with created with:
 * mysql> create table users(name varchar(10), password varchar(10), primary key (name));
 * <p>The administrator can be added with:
 * mysql> insert into users (name, password) values('admin', 'adminp');
 *
 * @author Martin Host
 * @version 1.0
 */
public class ServletBase extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Define states
    protected static final int LOGIN_FALSE = 0;
    protected static final int LOGIN_TRUE = 1;

    protected Connection connection;


    /**
     * Constructs a servlet and makes a connection to the database.
     * It also writes all user names on the console for test purpose.
     */
    public ServletBase() {
        Database database = new Database();
        connection = database.getConnection();
    }

    /**
     * Checks if a user is logged in or not.
     *
     * @param request The HTTP Servlet request (so that the session can be found)
     * @return true if the user is logged in, otherwise false.
     */
    protected boolean loggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Object objectState = session.getAttribute("state");
        int state = LOGIN_FALSE;
        if (objectState != null)
            state = (Integer) objectState;
        return (state == LOGIN_TRUE);
    }

    /**
     * Can be used to construct form elements.
     *
     * @param par Input string
     * @return output string = "par"
     */
    protected String formElement(String par) {
        return '"' + par + '"';
    }


    /**
     * Constructs the header of all servlets.
     *
     * @return String with html code for the header.
     */

    protected String getFullName(String username) {
        String fullName = "";
        try {
            PreparedStatement statement = connection.prepareStatement("select * from Users where username = ?");
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                fullName = set.getString("name");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }
    protected String getUserFullName(String username) {
        String fullName = "";
        try {
            PreparedStatement statement = connection.prepareStatement("select * from Users where username = ?");
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                fullName = set.getString("name");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }


    protected String encryptPassword(String password) {
        try {
            return encryptPassword(password, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String encryptPassword(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] hash = digest.digest(password.getBytes());
        return bytesToStringHex(hash);
    }

    private final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String bytesToStringHex(byte[] bytes) {
        char[] hexChar = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChar[i * 2] = hexArray[v >>> 4];
            hexChar[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChar);
    }



}
