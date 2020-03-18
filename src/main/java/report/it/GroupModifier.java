package report.it;

import report.it.models.Project;
import report.it.models.ProjectMember;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            request.setAttribute("navbarTitle", currentProject.getName());
            request.getRequestDispatcher("modfiy-project-header.jsp").include(request, response);
            request.getRequestDispatcher("navbar.jsp").include(request, response);

            List<ProjectMember> members = getMembers(currentProject.getName());
            System.out.println("Members size " + members.size());
            request.setAttribute("members", members);

            for (ProjectMember member: members) {
                System.out.println("Name: " + member.getName());
                System.out.println("Username: " + member.getUsername());
                System.out.println("Role: "  + member.getRole());
            }

            request.setAttribute("test", "test");
            request.getRequestDispatcher("modify-project.jsp").include(request, response);

            out.print("</div></body></html>");
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


    private List<ProjectMember> getMembers(String projectName) {
        ArrayList<ProjectMember> members = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select U.name, U.username, ProjectMembers.role\n" +
                    "from ProjectMembers join Users U on ProjectMembers.username = U.username\n" +
                    "join Projects P on ProjectMembers.projectId = P.id\n" +
                    "where P.name = ?;");
            preparedStatement.setString(1, projectName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                members.add(new ProjectMember(
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getInt("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public boolean addMemberToProject(String username, String project, int role) {
        boolean added = true;
        try {
            String query = "INSERT INTO ProjectMembers (username, projectId, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, project);
            preparedStatement.setInt(3, role);

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
                    " ProjectMembers.projectId = Projects.id\nWHERE name=? AND id=?";
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
