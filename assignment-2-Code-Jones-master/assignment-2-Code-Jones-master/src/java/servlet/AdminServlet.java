package servlet;

import dataaccess.UserDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;

public class adminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDB udb = new UserDB();
        String action = (String) request.getParameter("action");
        List<User> users = udb.getAll();
        request.setAttribute("users", users);
        HttpSession session = request.getSession();
        String accountEmail = (String) session.getAttribute("email");
        if (accountEmail != null) {
            User name = udb.get(accountEmail);
            request.setAttribute("name", (name.getFirstName() + " " + name.getLastName()));
        }

        try {
            if (action != null && action.equals("editUser")) {
                String thisUser = (String) request.getParameter("thisUser");
                User editUser = udb.get(thisUser);
                request.setAttribute("thisUser", editUser);
                request.setAttribute("heading", "Edit User");
            }
        } catch (Exception e) {
            action = null;
            request.setAttribute("heading", "Add User");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDB udb = new UserDB();
        String action = (String) request.getParameter("action");
        String email = (String) request.getParameter("email");
        String fname = (String) request.getParameter("fname");
        String lname = (String) request.getParameter("lname");
        String password = (String) request.getParameter("password");
        User user = new User(email, true, fname, lname, password);
        Role role = new Role(2);
        user.setRole(role);
        try {
            switch (action) {
                case "deleteUser":
                    String thisUser = (String) request.getParameter("thisUser");
                    User deleteMe = udb.get(thisUser);
                    udb.delete(deleteMe);
                    break;
                case "addUser":
                    if (email != null) {
                        if (udb.addUser(user)) {
                            request.setAttribute("message", "New User successfully added");
                        } else {
                            request.setAttribute("message", "New User could not be added");
                        }
                    } else {
                        request.setAttribute("message", "Please fill out all the inputs");
                    }
                    break;
                case "save":
                    request.setAttribute("heading", "Add User");
                    request.setAttribute("message", "User Successfully edited");
                    udb.update(user);
                    break;

            }
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            Logger.getLogger(adminServlet.class.getName()).log(Level.WARNING, null, e);
        }

        List<User> users = udb.getAll();
        request.setAttribute("users", users);

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
