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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class is for modifying project groups.
 *
 * @author Ehsanolah Hafezi & Lukas Sundberg
 * @version 0.3
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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if (nameObj != null)
            currentUsername = (String) nameObj;  // if the name exists typecast the name to a string
        request.setAttribute("user", currentUsername);
        PreparedStatement preparedStatement = null;
        Vector v = new Vector();
        try {
            preparedStatement = connection.prepareStatement("SELECT role FROM ProjectMembers WHERE username = ?");
            preparedStatement.setString(1, currentUsername);
            ResultSet set = preparedStatement.executeQuery();
            set.last();
            int size = set.getRow();
            set.first();
            System.out.println(size);
            for (int i = 0; i < size; i++){
                if (set.next()) {
                    v.add(set.getInt("role"));
                }
            }
            } catch(SQLException e){
                e.printStackTrace();
            }

        // check that the user is logged in and is admin/leader
        if (!loggedIn(request) || v.contains(2) || v.contains(3) || v.contains(4) || v.contains(5)) {
            response.sendRedirect("LogIn");
        } else {

            request.setAttribute("navbarTitle", getProjectName(currentProject.getId()));

            request.getRequestDispatcher("modfiy-project-header.jsp").include(request, response);
            request.getRequestDispatcher("navbar.jsp").include(request, response);

            request.setAttribute("members", getMembers(currentProject.getId()));

            request.setAttribute("test", "test");
            request.getRequestDispatcher("modify-project.jsp").include(request, response);

            String action = request.getParameter("action");

            if (action != null) {
                if (action.equals("update")) {
                    System.out.println("update member");
                    request.setAttribute("memberUsername", request.getParameter("memberUsername"));
                    System.out.println("MemberUserName: " + request.getParameter("memberUsername"));
                    request.getRequestDispatcher("edit-member.jsp").include(request, response);
                }
            }

            out.print("</div></body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        switch (request.getParameter("action")) {
            case "confirmUpdate":
                try {
                    String username = request.getParameter("memberUsername");
                    String role = request.getParameter("member-role");

                    System.out.println("Member to edit: ");
                    System.out.println("Role: " + role);
                    System.out.println("Username: " + username);

                    changeMemberRole(username, currentProject.getId(), Integer.parseInt(role));
                } catch (Exception e) {
                    System.out.println("invalid");
                }

                break;
            case "removeMember":

                removeMemberFromProject(
                        request.getParameter("memberUsername"),
                        currentProject.getId()
                );
                break;

            case "addMember":

                try {
                    addMemberToProject(
                            request.getParameter("newMemberUserName"),
                            currentProject.getId(),
                            Integer.parseInt(request.getParameter("member-role"))
                    );
                } catch (Exception e) {
                    System.out.println("invalid username or not choose group role");
                }
                break;

            case "changeProjectName":
                String projectName = request.getParameter("new-project-name");
                System.out.println("project to update: " + projectName);
                changeProjectName(projectName);
                break;

            case "changeMemberRole":
                changeMemberRole(
                        request.getParameter("MemberUserName"),
                        currentProject.getId(),
                        Integer.parseInt(request.getParameter("member-role"))
                );

                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }


    private String getProjectName(int projectId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT name FROM Projects WHERE id = ?");
            preparedStatement.setInt(1, projectId);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return set.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<ProjectMember> getMembers(int projectId) {
        List<ProjectMember> members = new ArrayList<ProjectMember>();

        try {
            String query = "select U.name, U.username," +
                    " ProjectMembers.role\n" +
                    " from ProjectMembers join Users U " +
                    " on ProjectMembers.username = U.username\n" +
                    "join Projects P on ProjectMembers.projectId = P.id\n" +
                    "where P.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
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

    /**
     * Add a member to a certain project group
     *
     * @param username, projectId, role
     * @return true if member added.
     */
    public boolean addMemberToProject(String username, int projectId, int role) {
        boolean added = true;
        try {
            String query = "INSERT INTO ProjectMembers (username, projectId, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, projectId);
            preparedStatement.setInt(3, role);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            added = false;
            e.printStackTrace();
        }
        return added;
    }

    /**
     * Removes a member from a certain project
     *
     * @param username,projectId
     * @return true if member removed.
     */
    public boolean removeMemberFromProject(String username, int projectId) {

        boolean removed = true;
        try {
            String query = "DELETE FROM ProjectMembers WHERE username = ? AND  projectId = ?";
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

    /**
     * Modify role of a member in a certain project
     *
     * @param username, projectId, role
     * @return true if the change has succeed.
     */
    public boolean changeMemberRole(String username, int projectId, int role) {

        boolean changed = true;
        try {
            String query = "UPDATE ProjectMembers SET role = ? WHERE username = ? AND projectId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, role);
            preparedStatement.setString(2, username);
            preparedStatement.setInt(3, projectId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            changed = false;
            e.printStackTrace();
        }
        return changed;
    }

    /**
     * Modify the name of a project
     *
     * @param newName name of project
     * @return true if the change has succeed.
     */
    public boolean changeProjectName(String newName) {
        boolean changed = true;
        try {
            String query = "UPDATE Projects SET name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, currentProject.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            changed = false;
            e.printStackTrace();
        }
        return changed;
    }
}
