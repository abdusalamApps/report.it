package report.it;


import report.it.models.Project;
import report.it.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Profile.
 * Constructs a page for Profile purpose.
 * It displays the user information and a form to chang the user's password.
 *
 * @author Aml Abbas & Milad Amini
 * @version 0.2
 */

@WebServlet("/Profile")
public class Profile extends ServletBase {

    private String currentUsername = "";
    private String currentName = "";
    private String currentEmail = "";

    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();


        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");

        if (nameObj != null) {
            currentUsername = (String) nameObj;
            currentName= getFullName(currentUsername);
            currentEmail= getEmail();
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {

            request.setAttribute("userName",currentName);
            request.setAttribute("userUsername",currentUsername);
            request.setAttribute("userEmail",currentEmail);

            List<Project> projects=getProjects();

            request.setAttribute("projects", projects);

            request.getRequestDispatcher("profile-header.jsp").include(request, response);

            request.setAttribute("navbarTitle", "Welcome " + currentName);
            System.out.println("Current full name: " + currentName);
            System.out.println("Current username: " + currentUsername);

            request.getRequestDispatcher("navbar.jsp").include(request, response);
            request.getRequestDispatcher("profile.jsp").include(request, response);

            out.println("</div>");
            out.println("</body></html>");

        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        switch (request.getParameter("action")) {
            case "changePassword":
                System.out.println("action changePassword");

                String currentPassword = request.getParameter("password");
                String newPassword1 = request.getParameter("newPassword1");
                String newPassword2 = request.getParameter("newPassword2");

                if (checkCurrentPassword(currentPassword)) {
                    request.setAttribute("newPasswordMessage", changePassword(newPassword1, newPassword2));
                } else {
                    request.setAttribute("newPasswordMessage" , "The password you entered is incorrect!");
                }

                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

    private boolean checkCurrentPassword(String currentPassword) {
        String rightPassword = "";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "select password from Users where username = ?");
            preparedStatement.setString(1, currentUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rightPassword = resultSet.getString("password");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return rightPassword.equals(encryptPassword(currentPassword));
    }

    private String changePassword(String newPassword1, String newPassword2) {
    int sCharacters = 0;
    int uppercase = 0;
    int digits = 0;
        for(char c : newPassword1.toCharArray())
        {
            if(Character.isUpperCase(c)) uppercase++;
            if(Character.isDigit(c)) digits++;
            if (!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122)))
            {
                sCharacters++;
            }
        }
        
        if(sCharacters>0){
            return "The new password contains special characters that is not allowed";
        }
        else if (digits<2)
        {
            return "The new password should contain atleast two digits";
        }
        else if (uppercase<1)
        {
            return "The new password should contain atleast one capital letter";
        }
        else if (!newPassword1.equals(newPassword2))
        {
            return "The new password doesn't match";
        } 
        else if(newPassword1.length() < 6 || newPassword1.length() > 12) 
        {
            return "The new password should be between 6-12 letters";
        }
       
        

        else {
            try {
                PreparedStatement stm =
                        connection.prepareStatement("update Users set password = ? where username = ?");
                stm.setString(1, encryptPassword(newPassword1));
                stm.setString(2, currentUsername);
                stm.executeUpdate();
                return "Your new password is " + newPassword1;

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            return "Something went wrong!";
        }
    }

    private String getEmail() {
        try {
            String sql="select * from Users where username='"+ currentUsername+"'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs= stm.executeQuery();
            while(rs.next()){
                return rs.getString("email");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "Coudln't find the email";
    }

    private List<Project> getProjects() {
    List<Project> projects= new ArrayList<>();
        try {
            String sql="select name from Projects where id in(select projectId from ProjectMembers where username='"+currentUsername+"')";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs= stm.executeQuery();
            while(rs.next()){
                String ProjectName= rs.getString("name");
                projects.add(new Project(ProjectName));
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        //   projects.add(new Project("pusp"));
        // projects.add(new Project("kursvård"));
    return projects;
    }
}