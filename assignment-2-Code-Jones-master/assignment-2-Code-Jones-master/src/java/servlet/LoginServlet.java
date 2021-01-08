package servlet;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

public class loginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("email") != null) {
            request.setAttribute("message", "Log out succesful, Cya next time");
        }
        session.invalidate(); // just by going to the login page the user is logged out :-)
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDB udb = new UserDB();
        User user = udb.login(email, password);

        if (user == null) {
            if (email.equals("")) {
                request.setAttribute("message", "Please Enter Email");
            } else if (password.equals("")) {
                request.setAttribute("message", "Please Enter Password");
            } else {
                request.setAttribute("message", "Failed Login");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("email", email);

        if (user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 3) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("inventory");
        }

    }
}
