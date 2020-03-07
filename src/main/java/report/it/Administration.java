package report.it;

import report.it.models.Project;
import report.it.models.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        String currentUsername = "";

        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");
        if (nameObj != null)
            currentUsername = (String) nameObj;  // if the name exists typecast the name to a string



        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {
            request.setAttribute("fullName", getFullName(currentUsername));

            request.getRequestDispatcher("administration.jsp").include(request, response);


            List<User> users = getUsers();

            request.setAttribute("users", users);

            request.getRequestDispatcher("all-users-table.jsp").include(request, response);
            request.getRequestDispatcher("add-user-form.jsp").include(request, response);
            request.getRequestDispatcher("add-project-form.jsp").include(request, response);
            request.getRequestDispatcher("all-projects-table.jsp").include(request, response);

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
                User user = null;
                try {
                    user = new User(
                            request.getParameter("username"),
                            request.getParameter("name"),
                            createPassword(),
                            request.getParameter("email")
                    );
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (checkNewName(user.getName())) {
                    addUser(user);
                } else {
                    System.out.println("invalied name");
                }
                break;
            case "addProject":
                System.out.println("action addProject");
                System.out.println("project to add: " + request.getParameter("projectname"));
                Project project = new Project(request.getParameter("projectname"));
                addProject(project);
                break;
            case "editProject":
                System.out.println("action editProject");
                System.out.println("project to add: " + request.getParameter("projectname"));
                response.sendRedirect("modify-project.jsp");
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
    private String createPassword() throws NoSuchAlgorithmException {
        String result = "";
        Random r = new Random();
        for (int i = 0; i < PASSWORD_LENGTH; i++)
            result += (char) (r.nextInt(26) + 97); // 122-97+1=26
        // TODO: encrypt password after creation

        return encryptPassword(result, "SHA-256");
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

    /**
     * Adds a user and a randomly generated password to the database.
     *
     * @param user User to be added
     * @return true if it was possible to add the name. False if it was not, e.g.
     * because the name already exist in the database.
     */
    private boolean addUser(User user) {
        boolean resultOk = true;
        try {
            Statement stmt = connection.createStatement();
            String statement = "insert into Users (username, name, password,email) values('" + user.getUsername() + "', '" + user.getName() + "', '" +
                    createPassword() + "', '" + user.getEmail() + "')";
            System.out.println(statement);
            stmt.executeUpdate(statement);
            stmt.close();

        } catch (SQLException | NoSuchAlgorithmException ex) {
            resultOk = false;
            System.out.println("SQLException: " + ex.getMessage());

        }
        return resultOk;
    }

    private boolean addProject(Project project) {
        boolean resultOk = true;
        try {
            Statement stmt = connection.createStatement();
            String statement = "insert into Projects (name) values('" + project.getName() + "')";
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

                users.add(new User(
                        username,
                        name,
                        password,
                        email
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

    private List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            String query = "select * from Projects";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                projects.add(new Project(name));
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return projects;
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

    private boolean deleteProject(String projectname) {
        boolean ok = true;

        try {
            String query = "delete from Projects where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, projectname);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            ok = false;
            e.printStackTrace();
        }
        return ok;
    }

    // User's association with any projects has to be deleted as well
    private boolean deleteAssociation(String username) {
        boolean ok = true;
        try {
            String query = "delete from ProjectMembers where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            ok = false;
            e.printStackTrace();
        }
        return ok;
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
    private String getFullName(String username) {
        String fullName = "";
        try {
            PreparedStatement statement = connection.prepareStatement("select * from Administrators where username = ?");
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
}