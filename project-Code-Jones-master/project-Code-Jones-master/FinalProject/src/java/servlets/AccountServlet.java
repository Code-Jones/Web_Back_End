package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import utilities.CookieUtil;

/**
 *
 * @author 786524
 */
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
             // maybe fix ? 
            Cookie[] cookies = request.getCookies();
            String cookieEmail = CookieUtil.getCookieValue(cookies, "email");
            
            UserDB udb = new UserDB();
            User accountUser = udb.get(cookieEmail);
//            User accountUser = udb.get((String) session.getAttribute("email"));

           

            request.setAttribute("fullName", accountUser.getFirstName() + " " + accountUser.getLastName());
            request.setAttribute("first", accountUser.getFirstName());
            request.setAttribute("last", accountUser.getLastName());
            request.setAttribute("email", accountUser.getEmail());
            
//            request.setAttribute("password", accountUser.getPassword()); // user reset password
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Weird, your details should be here... don't be messing with my cookies now");
        } finally {
            getServletContext().getRequestDispatcher("/WEB-INF/Account.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            UserDB udb = new UserDB();
            User accountUser = udb.get((String) session.getAttribute("email"));
            String action = request.getParameter("action");

            switch (action) {
                case "deactivate":
                    try {
                        accountUser.setActive(false);
                        udb.update(accountUser);
                        response.sendRedirect("Login");
                    } catch (Exception ex) {
                        Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "edit":
                    try {
                        String first = request.getParameter("updateFirst");
                        String last = request.getParameter("updateLast");
                        String email = request.getParameter("updateEmail");
                        String password = request.getParameter("updatePassword");

                        accountUser.setFirstName(first);
                        accountUser.setLastName(last);
                        accountUser.setEmail(email);
                        accountUser.setPassword(password);

                        udb.update(accountUser);
                        session.setAttribute("email", accountUser.getEmail());
                        request.setAttribute("message", "Your account information has been updated");

                        request.setAttribute("email", accountUser.getEmail());
                        request.setAttribute("first", accountUser.getFirstName());
                        request.setAttribute("last", accountUser.getLastName());
                        request.setAttribute("email", accountUser.getEmail());
                        request.setAttribute("password", accountUser.getPassword());
                    } catch (Exception ex) {
                        Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    request.setAttribute("message", "Your doing something you shouldn't be ... Aaron");
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Whoops, something went wrong");
        } finally {
            // not sure if this can be a thing since the redirect ? 
//            getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/Account.jsp").forward(request, response);
    }
}
