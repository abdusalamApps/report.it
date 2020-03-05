package report.it;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
This class is for modifying project groups.
 */
public class GroupModifier extends ServletBase {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public boolean addUserToGroup(String username, String project) {
        return false;
    }

    // removes a member from a certain project

    public boolean removeUserFromProject(String username, String project) {

        return false;
    }

    public boolean addMemberToProject(String username, String project, String role) {
        return false;
    }

    public boolean changeMemberRole(String username, String role) {
        return false;
    }

    public boolean changeProjectName(String newName, int id) {
        return false;
    }

}
