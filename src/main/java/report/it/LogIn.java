package report.it;

import org.checkerframework.checker.units.qual.A;

import java.io.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogIn
 * <p>
 * A log-in page.
 * <p>
 * The first thing that happens is that the user is logged out if he/she is logged in.
 * Then the user is asked for name and password.
 * If the user is logged in he/she is directed to the functionality page.
 *
 * @author Aml Abbas & Fredrik Peterson
 * @version 0.1
 */
@WebServlet("/LogIn")
public class LogIn extends ServletBase {
    private static final long serialVersionUID = 1L;
    private static int attempts = 0;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
        super();
    }

    /**
     * Implementation of all input to the servlet. All post-messages are forwarded to this method.
     * <p>
     * First logout the user, then check if he/she has provided a username and a password.
     * If he/she has, it is checked with the database and if it matches then the session state is
     * changed to login, the username that is saved in the session is updated, and the user is
     * relocated to the functionality page.
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get the session
        HttpSession session = request.getSession(true);

        int state;

        PrintWriter out = response.getWriter();

        if (loggedIn(request)) {
            session.setAttribute("state", LOGIN_FALSE);
            out.println("<p>You are now logged out</p>");
        }

        String username;
        String password;

        username = request.getParameter("user"); // get the string that the user entered in the form
        password = request.getParameter("password"); // get the entered password

        if (username != null && password != null) {
            if (getLoginAttempts(username) > 2) {
                setLogInAttempteDate(username);
                out.println("<p>you are locked for 10 minutes. </p>");
                request.getRequestDispatcher("index.jsp").include(request, response);

            } else {
                if (!checkUser(username).equals("")) {
                    long unixTimeLogIn =  System.currentTimeMillis() / 1000;
                    if (unixTimeLogIn< getLogInAttemptDate(username)){
                        out.println("<p>you are locked for 10 minutes. </p>");
                        request.getRequestDispatcher("index.jsp").include(request, response);

                    }
                    if (unixTimeLogIn> getLogInAttemptDate(username)) {
                        if (checkUser(username).equals(encryptPassword(password))) {
                            state = LOGIN_TRUE;
                            session.setAttribute("state", state);  // save the state in the session
                            session.setAttribute("username", username);  // save the username in the session
                            if (username.equals("admin")) {
                                response.sendRedirect("Administration");
                            } else {
                                response.sendRedirect("TimeReporting");
                            }
                            attempts = 0;
                        } else {
                            attempts++;
                            out.println("<p>You tried: " + attempts + "That was not a valid password. </p>");
                            request.getRequestDispatcher("index.jsp").include(request, response);
                        }
                    }

                } else {
                    out.println("<p>That was not a valid username </p>");
                    request.getRequestDispatcher("index.jsp").include(request, response);
                }
                setAttempts(username);
            }
        } else { // username was null, probably because no form has been filled out yet. Display form.
//            out.println(loginRequestForm());
            request.getRequestDispatcher("index.jsp").include(request, response);
        }

        out.println("</body></html>");
    }


    /**
     * All requests are forwarded to the doGet method.
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Checks with the database if the user should be accepted
     *
     * @param name The name of the user
     * @return true if the user should be accepted
     */
    private String checkUser(String name) {

        boolean userOk = false;
        boolean userChecked = false;
        String Password = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where username = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next() && !userChecked) {
                String nameSaved = rs.getString("username");
                Password = rs.getString("password");
                if (name.equals(nameSaved)) {
                    userChecked = true;
                    return Password;
                }
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "";
    }

    private int getLoginAttempts(String username) {

        try {
            String sql = "select attempts from userAttempts where username='" + username + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                attempts = rs.getInt("attempts");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return attempts;
    }

    private void setAttempts(String username) {
        try {
            String sql = "select username from userAttempts";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                if (rs.getString("username").equals(username)) {
                    updateAttempts(username);
                    return;
                }
            }
            sql = "insert into userAttempts (username, attempts) values('" + username + "','" + attempts + "')";
            stm = connection.prepareStatement(sql);
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void updateAttempts(String username) {
        try {

            String sql = "update userAttempts set attempts='" + attempts + "' where username='" + username + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void setLogInAttempteDate(String username) {
        resetAttempts(username);
        try {
            long unixTime = System.currentTimeMillis() / 1000;
            unixTime =unixTime+ 600;

            String sql = "update userAttempts set lastModified = '" + unixTime + "' where username='" + username + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private void resetAttempts(String username) {
        try {

            String sql = "update userAttempts set attempts=" + 0 + " where username='" + username + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private long getLogInAttemptDate(String username) {
        try {
            String sql = "select lastModified from userAttempts where username ='" + username + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getLong("lastModified");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return -1;
    }
}