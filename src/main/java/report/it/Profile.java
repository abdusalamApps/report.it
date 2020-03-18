package report.it;


import report.it.models.Project;
import report.it.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Profile")
public class Profile extends ServletBase {

    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();

        String currentUsername = "";

        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");
        if (nameObj != null){
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

        switch (request.getParameter("action")){
            case "changePassword":
                System.out.println("action changePassword");
                String oldPassword= request.getParameter("password");
                String newPassword1= request.getParameter("newPassword1");
                String newPassword2= request.getParameter("newPassword2");
                String username= request.getParameter("username");
                try {
                    request.setAttribute("yourPassword", changePassword(oldPassword,newPassword1,newPassword2, username));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

    private boolean checkCurrentPassword (String currentPassword){

        return false;
    }

    private String changePassword(String oldPassword, String newPassword1, String newPassword2, String username) throws SQLException {
     if (!newPassword1.equals(newPassword2)){
         return "newPassword1 and newPassword2 don't mach each other";
     }
     else{
         try {
         String query = "select password from Users where username="+username;
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet rs = preparedStatement.executeQuery();
             while (rs.next()) {
                if(oldPassword.equals(rs.getString("password"))){
                    String sql= "update Users set password= "+ newPassword1+" where username="+username;
                    PreparedStatement stm = connection.prepareStatement(sql);
                    stm.executeUpdate();
                    return "your Password changed successfuly ";
                }
             }
             preparedStatement.close();
         } catch (SQLException ex) {
             System.out.println("SQLException: " + ex.getMessage());
             System.out.println("SQLState: " + ex.getSQLState());
             System.out.println("VendorError: " + ex.getErrorCode());
         }
         return "your Password is wrong ";
     }
    }
}