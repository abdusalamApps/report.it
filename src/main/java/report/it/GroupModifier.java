package report.it;

import report.it.models.Project;

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

/*
This class is for modifying project groups.
 */
@WebServlet("/GroupModifier")

public class GroupModifier extends ServletBase {

    public static Project currentProject;

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
        request.setAttribute("user", currentUsername);

        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {

            System.out.println("currentProjectId: " + currentProject.getId());
            System.out.println("currentProjectName: " + currentProject.getName());

            request.getRequestDispatcher("modify-project.jsp").include(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch (request.getParameter("action")) {
            case "update":
                String username = request.getParameter("Member");
                String role = request.getParameter("role");
                System.out.println("User to update: " + username);
                changeMemberRole(username, currentProject.getId(), role);
                break;
            case "delete":
                username = request.getParameter("Member");
                System.out.println("User to delete: " + username);
                removeUserFromProject(username, currentProject.getId());
                break;
            case "addMember":
                username = request.getParameter("Member");
                role = request.getParameter("role");
                System.out.println("User to add: " + username);
                changeMemberRole(username, currentProject.getId(), role);
                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

    public boolean addUserToGroup(String username, String project) {
        boolean added = true;
        try {
            String query = "SELECT * FROM projectMembers WHERE username = ? and  project_name=?";
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

    public boolean removeUserFromProject(String username, int projectId) {

        boolean removed = true;
        try {
            String query = "DELETE FROM ProjectMembers WHERE username = ? and  projectId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, projectId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            removed = false;
            e.printStackTrace();
        }
        return removed;
    }

    public boolean addMemberToProject(String username, String project, String role) {
        boolean added = true;
        try {
            String query = "SELECT * FROM Users JOIN ProjectMembers ON \n" +
                          "Users.username = ProjectMembers.username JOIN Projects\n" +
                         " ON Users.name WHERE Users.name=? AND Projects.name=? AND role=?";
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

    public boolean changeMemberRole(String username, int projectId, String role) {

        boolean changed = true;
        try {
            String query = "SELECT * FROM ProjectMembers\n" +
                    "JOIN Projects P ON ProjectMembers.projectId = P.id\n" +
                    "WHERE username = ? AND projectId = ? AND role = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, projectId);
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
            String query = "SELECT * FROM Projects JOIN ProjectMembers ON " +
                         " ProjectMembers.projectId=projects.id\nWHERE name=? AND id=?";
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
