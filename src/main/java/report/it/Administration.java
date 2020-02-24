package report.it;

import report.it.models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Servlet implementation class Administration.
 * Constructs a page for administration purpose.
 * Checks first if the user is logged in and then if it is the administrator.
 * If that is OK it displays all users and a form for adding new users.
 *
 * @author Martin Host
 * @version 1.0
 */

@WebServlet("/Administration")
public class Administration extends ServletBase {

    private static final long serialVersionUID = 1L;
    private static final int PASSWORD_LENGTH = 6;

    /**
     * @see ServletBase#ServletBase()
     */
    public Administration() {
        super();
        // TODO Auto-generated constructor stub
    }


    /**
     * Handles input from the user and displays information for administration.
     * <p>
     * First it is checked if the user is logged in and that it is the administrator.
     * If that is the case all users are listed in a table and then a form for adding new users is shown.
     * <p>
     * Inputs are given with two HTTP input types:
     * addname: name to be added to the database (provided by the form)
     * deletename: name to be deleted from the database (provided by the URLs in the table)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println(getPageIntro());

        String myName = "";
        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("name");
        if (nameObj != null)
            myName = (String) nameObj;  // if the name exists typecast the name to a string

        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {

            request.getRequestDispatcher("administration.jsp").include(request, response);
            List<User> users = getUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("all-users-table.jsp").include(request, response);
            request.getRequestDispatcher("add-user-form.jsp").include(request, response);

            out.println("</div>");
            out.println("</body></html>");

        }
    }

    /**
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        switch (request.getParameter("action")) {
            case "delete":
                String username = request.getParameter("username");
                System.out.println("User to delete: " + username);
                deleteUser(username);
                break;
            case "add":
                System.out.println("action add");
                System.out.println("User to add: " + request.getParameter("username"));
                User user = new User(
                        request.getParameter("username"),
                        request.getParameter("name")
                );
                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

 /*   *//**
     * generates a form for adding new users
     *
     * @return HTML code for the form
     *//*
    private String addUserForm() {
        String html;
        html = "<p> <form name=" + formElement("input");
        html += " method=" + formElement("get");
        html += "<p> Add user name: <input type=" + formElement("text") + " name=" + formElement("addname") + '>';
        html += "<input type=" + formElement("submit") + "value=" + formElement("Add user") + '>';
        html += "</form>";
        return html;
    }*/

    /**
     * Checks if a username corresponds to the requirements for user names.
     *
     * @param name The investigated username
     * @return True if the username corresponds to the requirements
     */
    private boolean checkNewName(String name) {
        int length = name.length();
        boolean ok = (length >= 5 && length <= 10);
        if (ok)
            for (int i = 0; i < length; i++) {
                int ci = (int) name.charAt(i);
                boolean thisOk = ((ci >= 48 && ci <= 57) ||
                        (ci >= 65 && ci <= 90) ||
                        (ci >= 97 && ci <= 122));
                //String extra = (thisOk ? "OK" : "notOK");
                //System.out.println("bokst:" + name.charAt(i) + " " + (int)name.charAt(i) + " " + extra);
                ok = ok && thisOk;
            }
        return ok;
    }

    /**
     * Creates a random password.
     *
     * @return a randomly chosen password
     */
    private String createPassword() {
        String result = "";
        Random r = new Random();
        for (int i = 0; i < PASSWORD_LENGTH; i++)
            result += (char) (r.nextInt(26) + 97); // 122-97+1=26
        // TODO: encrypt password after creation
        return result;
    }

    private String encryptPassword(String password) {
        return "";
    }

    /**
     * Adds a user and a randomly generated password to the database.
     *
     * @param name Name to be added
     * @return true if it was possible to add the name. False if it was not, e.g.
     * because the name already exist in the database.
     */
    private boolean addUser(String name) {
        boolean resultOk = true;
        try {
            Statement stmt = connection.createStatement();
            String statement = "insert into Users (username, password) values('" + name + "', '" +
                    createPassword() + "')";
            System.out.println(statement);
            stmt.executeUpdate(statement);
            stmt.close();

        } catch (SQLException ex) {
            resultOk = false;
            // System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return resultOk;
    }


    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * \n" +
                    "from Users\n" +
                    "         left join ProjectMembers PM on Users.username = PM.username;\n";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String project = rs.getString("project_name");
                if (project == null) project = "Not associated";
                users.add(new User(
                        username,
                        name,
                        password,
                        email,
                        Integer.parseInt(role),
                        project
                ));
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return users;
    }

    private boolean deleteUser(String username) {
        boolean ok = true;
        deleteAssociation(username);
        try {
            String query = "delete from Users where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            ok = false;
            e.printStackTrace();
        }
        return ok;
    }


    // User's association with any projects has to be deleted as well
    private void deleteAssociation(String username) {
        try {
            String query = "delete from ProjectMembers where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteAssociation(String username, String project) {
        return false;
    }

    /**
     * Deletes a user from the database.
     * If the user does not exist in the database nothing happens.
     *
     * @param name name of user to be deleted.
     */
    /*
    private void deleteUser(String name) {
        try {
            Statement stmt = conn.createStatement();
            String statement = "delete from users where name='" + name + "'";
            System.out.println(statement);
            stmt.executeUpdate(statement);
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
*/
}
