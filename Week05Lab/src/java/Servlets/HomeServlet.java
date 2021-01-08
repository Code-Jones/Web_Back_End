package Servlets;

import Models.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService acct;
        String logout = request.getParameter("logout");
        acct = (AccountService) request.getSession().getAttribute("acct");
        if (!acct.isLoggedIn()) {
            request.getSession().invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        request.getSession().setAttribute("username", acct.getUsername());
        
        if (logout != null) {
            acct.logout();
            request.getSession().setAttribute("acct", acct);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService acct;
        String logout = request.getParameter("logout");
        acct = (AccountService) request.getSession().getAttribute("acct");
        if (logout != null) {
            acct.logout();
//            request.getSession().setAttribute("acct", acct);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }

}
