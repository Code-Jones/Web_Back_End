package Servlets;

import Models.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService acct;
        
        try { // there is a session
            acct = (AccountService) request.getSession().getAttribute("acct");
            if (acct.isLoggedIn()) { // is logged in
                request.getSession().setAttribute("acct", acct);
                getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            }
            request.getSession().setAttribute("acct", acct);
        } catch (Exception e) {
            acct = new AccountService();
            request.getSession().setAttribute("acct", acct);
        } 
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountService acct;
        
        
        try {
            // if there is a session
            acct = (AccountService) request.getSession().getAttribute("acct");
            if (acct.isLoggedIn()) { // already logged in
                getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            } else { // not logged in
                if (acct.login(username, password)) {
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                } else {
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            // no session
            acct = new AccountService(); // hard coded
            if (acct.login(username, password)) {
                // login successful 
                request.getSession().setAttribute("acct", acct);
                getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        }
    }
}
