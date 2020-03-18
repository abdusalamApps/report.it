package report.it;

import org.checkerframework.checker.units.qual.A;

import java.io.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 * @author Martin Host
 * @version 1.0
 */
@WebServlet("/LogIn")
public class LogIn extends ServletBase {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
        super();
        // TODO Auto-generated constructor stub

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
            if (checkUser(username, encryptPassword(password))) {
                state = LOGIN_TRUE;
                session.setAttribute("state", state);  // save the state in the session
                session.setAttribute("username", username);  // save the username in the session
                if (username.equals("admin")) {
                    response.sendRedirect("Administration");
                } else {
                    response.sendRedirect("TimeReporting");
                }
            } else {
                out.println("<p>That was not a valid user username / password. </p>");
                //                out.println(loginRequestForm());
                request.getRequestDispatcher("index.jsp").include(request, response);
            }

        } else { // username was null, probably because no form has been filled out yet. Display form.
//            out.println(loginRequestForm());
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
     * @param name     The name of the user
     * @param password The password of the user
     * @return true if the user should be accepted
     */
    private boolean checkUser(String name, String password) {

        boolean userOk = false;
        boolean userChecked = false;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Users");
            while (rs.next() && !userChecked) {
                String nameSaved = rs.getString("username");
                String passwordSaved = rs.getString("password");
                if (name.equals(nameSaved)) {
                    userChecked = true;
                    userOk = password.equals(passwordSaved);
                }
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return userOk;
    }

    private boolean checkAdmin(String username, String password) {
        boolean usernameExists = false;
        boolean passCorrect = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Administrators where username = ?");
            preparedStatement.setString(1, username);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next() && !usernameExists) {
                if (username.equals(set.getString("username"))) {
                    usernameExists = true;
                    passCorrect = set.getString("password").equals(password);
                }
            }
            preparedStatement.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return passCorrect;
    }


    private int checkLoginAttempts() {
        int fails = 0;

        return fails;
    }

}
