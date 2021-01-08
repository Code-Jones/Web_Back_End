package filter;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author 786524
 */
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest r = (HttpServletRequest) request;
        HttpSession session = r.getSession();
        UserDB udb = new UserDB();

        if (session.getAttribute("email") != null) {
            User user = udb.get((String) session.getAttribute("email"));
            if (user.getActive()) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.sendRedirect("Login");
            }
        } else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("Login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
