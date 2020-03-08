package report.it;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
This class is for modifying project groups.
 */
public class GroupModifier extends ServletBase {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // super.doGet(request, response);
        PrintWriter out = response.getWriter();
        out.println(getPageIntro());

        String currentUsername = "";

        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");
        if (nameObj != null)
            currentUsername = (String) nameObj;  // if the name exists typecast the name to a string
            request.setAttribute("user",currentUsername);

        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {
            request.getRequestDispatcher("GroupModifier.jsp").include(request, response);

        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

    }

    public boolean addUserToGroup(String username, String project) {
        boolean added = true;
        try {
            String query = "SELECT * FROM projectMembers WHWRE username = ? and  project_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, project);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            added = false;
            e.printStackTrace();
        }

        return added;
    }

    // removes a member from a certain project

    public boolean removeUserFromProject(String username, String project) {

        boolean ok = true;
        try {
            String query = "DELETE FROM ProjectMembers WHERE username = ? and  project_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, project);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            ok = false;
            e.printStackTrace();
        }

        return ok;
    }

    public boolean addMemberToProject(String username, String project, String role) {
        boolean added = true;
        try {
            String query = "SELECT * FROM ProjectMembers WHWRE username = ? and  project_name=? and role=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, project);
            preparedStatement.setString(3, role);


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            added = false;
            e.printStackTrace();
        }

        return added;
    }

    public boolean changeMemberRole(String username,String project, String role) {

        boolean changed = true;
        try {
            String query = "ALTER TABLE ProjectMembers WHWRE username = ? and  project_name=? and role=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, project);
            preparedStatement.setString(3, role);


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            changed = false;
            e.printStackTrace();
        }

        return changed;
    }

    public boolean changeProjectName(String newName, int id) {
        boolean changed = true;
        try {
            String query = "ALTER TABLE Projects WHWRE name=? and id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            changed = false;
            e.printStackTrace();
        }

        return changed;
    }
}
