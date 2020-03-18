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

@WebServlet("/TimeReporting")
public class TimeReporting extends ServletBase {

    private static final long serialVersionUID = 1L;

    public TimeReporting(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String currentUsername = "";
        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");
        if (nameObj != null) {
            currentUsername = (String) nameObj;  // if the name exists typecast the name to a string
            request.setAttribute("user",currentUsername);
        }

        // check that the user is logged in
        if (!loggedIn(request)) {
            response.sendRedirect("LogIn");
        } else {
            request.setAttribute("navbar-title", "Welcome" + getFullName(currentUsername));

            request.getRequestDispatcher("timereporting-header.jsp").include(request, response);
            request.getRequestDispatcher("navbar.jsp").include(request, response);

            List<TimeReport> timeReports = getTimeReports(currentUsername);
            request.setAttribute("timeReports", timeReports);

            List<String> projects= getProjectName(currentUsername);
            request.setAttribute("projects", projects);
            request.getRequestDispatcher("timereports-table.jsp").include(request, response);

            if (isLeader(currentUsername)) {
                List<TimeReport> groupReports= getGroupTimeReport(currentUsername);
                request.setAttribute("groupReports",groupReports);

                List<Project> myGroups=getProject(currentUsername);
                request.setAttribute("myGroups",myGroups);
                request.getRequestDispatcher("leadr-groups-table.jsp").include(request, response);
            }
            out.println("</body></html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String myName = "";
        HttpSession session = request.getSession(true);
        Object nameObj = session.getAttribute("username");

        if (nameObj != null) {
            myName = (String) nameObj;  // if the name exists typecast the name to a string
            request.setAttribute("user",myName);
        }

        switch (request.getParameter("action")) {
            case "editTimeReport":
                int reportId=Integer.parseInt(request.getParameter("timeReportId"));
                System.out.println("edit report id "+reportId);

                request.setAttribute("reportId",reportId);
                TimeReport editReport= getTimeReport(reportId);
                request.setAttribute("editReport",editReport);
                request.setAttribute("editable",true);

                break;
            case "editProject":
                response.sendRedirect("GroupModifier");
                break;

            case "sign":
                int groupReportId=Integer.parseInt(request.getParameter("groupReportId"));
                signReport(groupReportId);
                break;
            case "submit":
                System.out.println("action submit time report");
                boolean isSubmitted= submitReport(request, myName);

                //request.setAttribute("isSubmitted", isSubmitted);// out.print("Please check if you choose a project or input invalid text");

                break;

            case "update"://funkar inte
                int updateReportId=Integer.parseInt(request.getParameter("reportId"));
                boolean idUpdated=updateReport(request,updateReportId);
                System.out.println(idUpdated);
                request.setAttribute("editable",false);
                break;

            case "delete":
                int deleteReportId=Integer.parseInt(request.getParameter("reportId"));
                boolean isDeleted=deleteReport(deleteReportId);
                request.setAttribute("editable",false);
                break;

            default:
                System.out.println("no action selected");
                break;
        }
        doGet(request, response);
    }

    private boolean updateReport(HttpServletRequest request,int reportId){
        boolean idUpdated=true;
        try{int minutes=Integer.parseInt(request.getParameter("time"));
        int week=Integer.parseInt(request.getParameter("week"));
        String projectName=request.getParameter("projectName");
        int projectId=0;
        PreparedStatement ps;
        try {
            String query = "select * from Projects where name=?";
            ps = connection.prepareStatement(query);
            ps.setString(1,projectName);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                projectId=rs.getInt("id");}

        }catch (SQLException ex) {
            idUpdated=false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
         try{   String query = "update TimeReports set minutes_sum= ?, week=?, projectId=? where id=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1,minutes);
            ps.setInt(2,week);
            ps.setInt(3,projectId);
            ps.setInt(4, reportId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            idUpdated=false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        }catch(Exception e) {
            idUpdated=false;
        }
        return idUpdated;
    }

    private boolean deleteReport(int reportId){
        boolean isdeleted=true;
        PreparedStatement ps;
        try {
            String query = "delete from TimeReports where id=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, reportId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            isdeleted=false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return isdeleted;
    }
    /**
     * check if current user is a project leader
     * @param user current users username
     * @return true if the current user is a project leader, false if not
     */
    private boolean isLeader(String user) {
        int role = -1;
        try {
            String query = "select * from ProjectMembers where username = '"+user+"'";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                role = rs.getInt("role");
            ps.close();
        } catch (SQLException ex) {

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return role == 1;
    }

    private List<Project> getProject(String user){
        List<Project> projects= new ArrayList<>();
        PreparedStatement ps =null;
        try {
            String query = "select * from Projects inner join ProjectMembers on Projects.id= ProjectMembers.projectId where username=?";
            ps = connection.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id=rs.getInt("Projects.id");
                String name=rs.getString("Projects.name");
                projects.add(new Project(id,name));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return projects;
    }

    private boolean signReport(int reportID){
        boolean isSigned=true;
        PreparedStatement ps =null;
        try {

            String query = "update TimeReports set signed=true where id=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, reportID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            isSigned=false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return isSigned;
    }

    /**
     * get all members time reports in grouperLeaders group
     * @param groupLeader
     * @return list of all members time reports
     */
    private List<TimeReport> getGroupTimeReport(String groupLeader){
        List<TimeReport> groupReports = new ArrayList<>();
        PreparedStatement ps =null;
        try {
            //String query = "select distinct p1.username AS member from ProjectMembers p1, ProjectMembers p2 where p1.projectId=p2.projectId " +
            //        "and p2.username = ? and p2.role = 1";
            String query = "select distinct p1.username, p2.projectId from ProjectMembers p1 inner join ProjectMembers p2 on p1.projectId=p2.projectId " +
                    "where p2.username = ? and p2.role = '1'";
            ps = connection.prepareStatement(query);
            ps.setString(1, groupLeader);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int projectID=rs.getInt("p2.projectId");
                String groupMember= rs.getString("p1.username");
                List<TimeReport> memberReports=getTimeReports(groupMember);
                for (TimeReport t:memberReports) {
                    // add time report only with projectID of groupLeader as leader
                    if(t.getProjectID()==projectID)
                        groupReports.add(t);}
               // if(!groupMember.equals(groupLeader)){
                  // groupReports.addAll(getTimeReports(groupMember));
                   /* // add only unsigned time reports
                   List<TimeReport> memberReports=getTimeReports(groupMember);
                    for (TimeReport t:memberReports) {
                        // add unsigned time report only
                        if(!t.getSigned())
                            groupReports.add(t);
                    }*/
              //  }
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return groupReports;
    }

    /**
     * get current users all time reports
     * @param user current users username
     * @return a list of this users time report
     */
    private List<TimeReport> getTimeReports(String user){
        List<TimeReport> timeReports = new ArrayList<>();
        PreparedStatement ps =null;
        try {
            String query = "select * from TimeReports inner join Projects on Projects.id=TimeReports.projectId where username = ?" +
                    "order by week DESC";
            ps = connection.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id =rs.getInt("TimeReports.id");
                String submitted=rs.getString("submitted");
                int minutes_sum= rs.getInt("minutes_sum");
                boolean signed=rs.getBoolean("signed");
                int projectId=rs.getInt("projectId");
                String projectName=rs.getString("Projects.name");
                int week=rs.getInt("week");
                timeReports.add(new TimeReport(id, submitted,minutes_sum, signed, projectId, projectName, user, week));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return timeReports;
    }

    /**
     * get current users all project
     * @param user current users username
     * @returna a list of this users all project name
     */
    private List<String> getProjectName(String user){
        List<String> projects= new ArrayList<>();
        PreparedStatement ps =null;
        try {
            String query = "select Projects.name from Projects inner join ProjectMembers on Projects.id=ProjectMembers.projectId where username = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                projects.add(rs.getString("Projects.name"));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return projects;
    }


    private boolean submitReport(HttpServletRequest request, String username){
        boolean isSubmitted = true;
        PreparedStatement ps =null;
        //try catch is used to check if input is valid data
        try { int minutes=Integer.parseInt(request.getParameter("time"));
            int week=Integer.parseInt(request.getParameter("week"));
            String projectName=request.getParameter("projectName");
            //submit and update database
            try{
                String query="select id from Projects where name = '"+ projectName+"'";
                ps = connection.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                rs.next();
                int projectId = rs.getInt("id");

                String sql ="insert into TimeReports (submitted, minutes_sum, signed, projectId,username, week) "
                        +" values(?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setString(1, currentTime());
                ps.setInt(2,minutes);
                ps.setBoolean(3,false);
                ps.setInt(4,projectId);
                ps.setString(5,username);
                ps.setInt(6,week);
                ps.executeUpdate();

                ps.close();

            } catch (SQLException ex) {
                isSubmitted=false;
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }catch(Exception e) {
            isSubmitted=false;
        }
        return isSubmitted;
    }

    private String currentTime() {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }

    private TimeReport getTimeReport(int timeReportId){
        TimeReport timeReport=null;
        PreparedStatement ps =null;
        try {
            String query = "select * from TimeReports inner join Projects on Projects.id=TimeReports.projectId where TimeReports.id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, timeReportId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String submitted=rs.getString("submitted");
                int minutes_sum= rs.getInt("minutes_sum");
                boolean signed=rs.getBoolean("signed");
                int projectId=rs.getInt("projectId");
                String projectName=rs.getString("Projects.name");
                String user=rs.getString("username");
                int week=rs.getInt("week");
                timeReport=new TimeReport(timeReportId, submitted,minutes_sum, signed, projectId, projectName, user, week);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return timeReport;
    }

}
