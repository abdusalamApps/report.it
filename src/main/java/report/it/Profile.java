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
import java.util.List;

@WebServlet("/Profile")
public class Profile extends ServletBase {

    private String currentUsername = "";

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
        }

        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {
            request.setAttribute("navbarTitle", "Welcome " + getFullName(currentUsername));

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
        if (!newPassword1.equals(newPassword2)) {
            return "The new password doesn't match";
        } else {
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
}