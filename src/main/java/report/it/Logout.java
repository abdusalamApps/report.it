package report.it;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class LogOut.
 * Constructs a page for LogOut purpose.
 * Log out the user
 *
 * @author Aml Abbas
 * @version 0.3
 */

@WebServlet("/Logout")
public class Logout extends ServletBase{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        session.removeAttribute("state");
        session.invalidate();
        response.sendRedirect("LogIn");
    }

}
