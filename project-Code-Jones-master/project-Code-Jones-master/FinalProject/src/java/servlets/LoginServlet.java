package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import utilities.CookieUtil;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDB udb = new UserDB();
        String email = (String) session.getAttribute("email");
        if (email != null) {
            User user = udb.get(email);
            if (user.getActive()) {
                request.setAttribute("message", "Log out succesful, Cya next time");
            } else {
                request.setAttribute("message", "Your account has been deactivated");
            }
        }
        Cookie[] cookies = request.getCookies();
        String cookieEmail = CookieUtil.getCookieValue(cookies, "email");
        request.setAttribute("email", cookieEmail);

        session.invalidate(); // just by going to the login page the user is logged out :-)
        getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email.equals("") || password.equals("")) {
            request.setAttribute("message", "Please Enter your Information");
            getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
            return;
        }
        Cookie cookie = new Cookie("email", email);
        cookie.setMaxAge(60 * 60 * 24); // today
        response.addCookie(cookie);

        try {
            UserDB udb = new UserDB();
            User user = udb.get(email);
            if (user != null) {
                user = udb.login(email, password);

                if (user != null && user.getActive()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    // email specs
                    String path = getServletContext().getRealPath("/WEB-INF");
                    as.sendLoginEmail(email, "Inventory Login", path);
                    session.setAttribute("active", "true");
                    if (user.getRole().getRoleId() != 2) {
                        response.sendRedirect("Admin");
                    } else {
                        response.sendRedirect("Inventory");
                    }

                } else {
                    if (user == null) {
                        request.setAttribute("message", "No account regestered to that email");
                    } else if (!user.getActive()) {
                        request.setAttribute("message", "Your account has been deactivated");
                        response.sendRedirect("Login");
                    }
                }
            } else {
                request.setAttribute("message", "Whoopsie, Login didn't work, try again");
                response.sendRedirect("Login");
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
