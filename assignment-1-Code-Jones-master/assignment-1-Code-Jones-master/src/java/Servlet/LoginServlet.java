package Servlet;

import Models.AccountService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService acct = (AccountService) session.getAttribute("acct");

        if (acct != null) {
            if (acct.isLoggedIn()) {
                if (acct.getUsername().equals("admin")) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/inventory");
                }
            }
        } else {
            session.invalidate();
            session = request.getSession();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountService acct;
        String path = getServletContext().getRealPath("/WEB-INF/users.txt");
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;
        boolean found = false;
        if (username != null || username.equals("") || password != null || password.equals("")) {
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                if (username.equals(lines[0]) && password.equals(lines[1])) {
                    acct = new AccountService(username, password, true);
                    session.setAttribute("acct", acct);
                    if (username.equals("admin")) {
                        response.sendRedirect(request.getContextPath() + "/admin");
                        found = true;
                    } else {
                        response.sendRedirect(request.getContextPath() + "/inventory");
                        found = true;
                    }
                }
            }
            if (!found) {
                request.setAttribute("message", "Invalid entry");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Invalid entry");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
