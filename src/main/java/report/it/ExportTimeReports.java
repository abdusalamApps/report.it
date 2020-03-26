package report.it;

import report.it.models.Project;
import report.it.models.ProjectMember;
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Administration.
 * Constructs a page for administration purpose.
 * Checks first if the user is logged in and then if it is the administrator.
 * If that is OK it displays all users and a form for adding new users.
 *
 * @author Aml Abbas & Milad Amini
 * @version 0.3
 */

@WebServlet("/export-time-reports")
public class ExportTimeReports extends TimeReporting {

    private static final long serialVersionUID = 1L;

    /**
     * @see ServletBase#ServletBase()
     */
    public ExportTimeReports() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String currentUsername = "";

        HttpSession session = request.getSession(true);

        session.setMaxInactiveInterval(20*60);

        Object nameObj = session.getAttribute("username");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.

        if (nameObj != null)
            currentUsername = (String) nameObj;  // if the name exists typecast the name to a string


        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {
           // if(isleader(currentUsername)) {
                List<TimeReport> groupReports = getGroupTimeReport(currentUsername);
            
                /*

                //Loops through the list, builds a string. Need to open it in new browser window.

                for(int i = 0; i < groupReports.size(); i++){

                    res += "Username, ProjectName, week, minutes\n";

                    res += groupReports.get(i).getUsername() + ", " +
                            groupReports.get(i).getProjectName() + ", " +
                            groupReports.get(i).getWeek() + ", " +
                            groupReports.get(i).getMinutes_sum() + "\n";


                }
            System.out.println(res);

    */    
            
                request.setAttribute("groupReports", groupReports);
            request.getRequestDispatcher("export-time-report.jsp").include(request, response);
                out.println("</div>");
                out.println("</body></html>");
            }
        //}
    }

    /**
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doGet(request, response);
    }


   }
