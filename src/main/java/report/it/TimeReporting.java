package report.it;

import report.it.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
            request.getRequestDispatcher("timereports-table.jsp").include(request, response);

            if (leader(myName)) {
                request.getRequestDispatcher("leadr-groups-table.jsp").include(request, response);
            }

            out.println("</body></html>");


        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public boolean leader(String username) {
        return false;
    }
}
