package report.it;

import report.it.models.Project;
import report.it.models.TimeReport;
import report.it.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TimeReporting")
public class TimeReporting extends ServletBase {

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

            request.getRequestDispatcher("timereporting-header.jsp").include(request, response);

            List<TimeReport> timeReports=getTimeReports(myName);
            request.setAttribute("timeReports", timeReports);
            List<String> projects= getProjectName(myName);
            request.setAttribute("projects",projects);
            request.getRequestDispatcher("timereports-table.jsp").include(request, response);

            if (isLeader(myName)) {
                request.getRequestDispatcher("leadr-groups-table.jsp").include(request, response);
            }

            out.println("</body></html>");

        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String username=(String) session.getAttribute("name");
        PrintWriter out = response.getWriter();

        switch (request.getParameter("action")) {
            case "edit":

                break;
            case "sign":

                break;
            case "submit":

                break;
            case "update":

                break;
            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

    /**
     * check if current user is a project leader
     * @param username current users username
     * @return true if the current user is a project leader, false if not
     */
    private boolean isLeader(String username) {
        int role = -1;
        try {
            String query = "select role from ProjectMembers where username = "+username;
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            rs.next();
                role = rs.getInt("role");
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return role == 1;
    }

    /**
     * get current users all time reports
     * @param user current users username
     * @return a list of this users time report
     */
    private List<TimeReport> getTimeReports(String user){
        List<TimeReport> timeReports = new ArrayList<>();

        return timeReports;
    }

    /**
     * get current users all project
     * @param user current users username
     * @returna a list of this users all project name
     */
    private List<String> getProjectName(String user){
        List<String> projects= new ArrayList<>();

        return projects;
    }


}
