package filters;

import ca.sait.securitydemo12.dataaccess.UserDB;
import ca.sait.securitydemo12.models.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andyd
 */
@WebFilter(filterName = "AdminFilter", servletNames = {"AdminServlet"})
public class AdminFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        String email = (String) session.getAttribute("email");
        UserDB userDb = new UserDB(); 
        User user = userDb.get(email);

        int roleId = user.getRole().getRoleId();

        if (roleId == 2) {
            httpResponse.sendRedirect("notes"); //
        }
        if (roleId == 1) {
            session.setAttribute("message", "Welcome Admin");
            chain.doFilter(request, response);
        }
    }

//        if (roleId == null) {
//            session.setAttribute("message", "Try again!");
//            httpResponse.sendRedirect("login");
//        }
    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {

    }
}
