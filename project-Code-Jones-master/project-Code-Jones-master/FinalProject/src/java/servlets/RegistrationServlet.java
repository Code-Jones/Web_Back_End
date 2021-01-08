package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.AccountService;
import utilities.PasswordAuth;

/**
 *
 * @author 786524
 */
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        UserDB udb = new UserDB();

        String verification = request.getParameter("verification");
        if (verification == null || verification.equals("")) {
            getServletContext().getRequestDispatcher("/WEB-INF/Registration.jsp").forward(request, response);
        } else if (verification.equals("true")) {
            request.setAttribute("verification", null);
            request.setAttribute("message", "User registered successfully!");

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstname = request.getParameter("first");
            String lastname = request.getParameter("last");

            try {
                User user = new User(email, true, firstname, lastname, password);
                Role role = new Role(2);
                user.setRole(role);
                udb.addUser(user);
            } catch (Exception ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            // salt version
//            try {
//                PasswordAuth pa = new PasswordAuth();
//                String salt = (String) session.getAttribute("salt");
//                String saltedPass = pa.hashAndSaltPassword(password, salt);
//
//                User user = new User(email, true, firstname, lastname, saltedPass);
//                user.setSalt(salt);
//                Role role = new Role(2);
//                user.setRole(role);
//                udb.addUser(user);
//            } catch (NoSuchAlgorithmException ex) {
//                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }

            String path = getServletContext().getRealPath("/WEB-INF");
            as.sendSignedUpEmail(email, "Thanks for signing up", path, firstname, lastname);

            //invalidates salt
            getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/Registration.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String first = request.getParameter("firstname");
        String last = request.getParameter("lastname");

        // behold the mighty tree of if statements .. this is bad programming fuuuursure
        if (email != null && !email.isEmpty()) {
            if (password != null && !password.isEmpty()) {
                if (confirmPassword != null && !confirmPassword.isEmpty()) {
                    if (password.contentEquals(confirmPassword)) {
                        if (first != null && !first.isEmpty()) {
                            if (last != null && !last.isEmpty()) {
                                request.setAttribute("email", email);
                                request.setAttribute("firstname", first);
                                request.setAttribute("lastname", last);

                                // okay so salting isn't working so i'm commenting this out i guess
//                                    PasswordAuth pa = new PasswordAuth();
//                                    String salt = pa.getSalt();
//                                    String saltedPass = pa.hashAndSaltPassword(password, salt);
//                                    Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, saltedPass);
//                                    session.setAttribute("salt", salt);
                                String path = getServletContext().getRealPath("/WEB-INF");
                                String url = request.getRequestURL().toString();
//                                    as.registerUser(email, "Verify Registration", path, url, saltedPass, first, last);
                                as.registerUser(email, "Verify Registration", path, url, password, first, last);
                                request.setAttribute("message", "Please Check your email to verify your account");
                            } else {
                                request.setAttribute("message", "Please enter your last name");
                                response.sendRedirect("Registration");
                            }
                        } else {
                            request.setAttribute("message", "Please enter your first name");
                            response.sendRedirect("Registration");
                        }
                    } else {
                        request.setAttribute("message", "Password and confirmation password have to match");
                        response.sendRedirect("Registration");
                    }
                } else {
                    request.setAttribute("message", "Please enter confirmation password");
                    response.sendRedirect("Registration");
                }
            } else {
                request.setAttribute("message", "Please enter password");
                response.sendRedirect("Registration");
            }
        } else {
            request.setAttribute("message", "Please enter email");
            response.sendRedirect("Registration");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/Registration.jsp").forward(request, response);
    }
}
