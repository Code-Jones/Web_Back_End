package servlets;

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
import services.RoleService;
import services.UserService;

/*
    doGet: get session and display user.jsp
    doPost: get "action" parameter and add, edit, delete, save cancel using case statement
 */

/**
 * Servlet responsible for all processing on web page
 */
public class UserServlet extends HttpServlet {

    /**
     * Gets the session and displays the user.jsp based on status
     * Populates List for users
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService();
        RoleService roleService = new RoleService();

        HttpSession session = request.getSession();

        String pageStatus = (String) session.getAttribute("pageStatus");

        // when page status session is null then display the default page.
        if (pageStatus == null) {
            session.setAttribute("pageStatus", "display");
            response.sendRedirect("User");
            return;
        }

        // change user form in 'user.jsp' as per the the diplay mode such as default, add or edit user
        switch (pageStatus) {
            case "display": // default display. also used after save, delete user or cancel.
                request.setAttribute("defaultTitle", true);
                request.setAttribute("enableForm", false);
                request.setAttribute("cancelForm", false);
                break;
            case "addUser": // add user display mode
                request.setAttribute("addUser", true);
                request.setAttribute("enableForm", true);
                request.setAttribute("cancelForm", true);
                break;
            case "editUser": // edit user display mode
                request.setAttribute("editUser", true);
                request.setAttribute("enableForm", true);                
                request.setAttribute("cancelForm", true);
                break;
        }

        // pass users and roles to user.jsp
        try {
            List<User> users = userService.getAll();
            List<Role> roles = roleService.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    /**
     * Responsible for all actions performed on Users
     * Based off of action parameter it can: add a user, edit a user, delete a user or save new user info
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        String action = request.getParameter("action");

        switch (action) {
            // when the press the "Add User" button of the user form
            case "addUser":
                session.setAttribute("userToEdit", null); // make null for the userToEdit not to display in the user form
                session.setAttribute("pageStatus", "addUser");
                break;
            // when the press the "Edit" button of the user in the table
            // it passes the email address for SQL query                
            case "editUser": {
                try {
                    String key = request.getParameter("key");
                    User user = userService.get(key);                    
                    session.setAttribute("userToEdit", user);
                    session.setAttribute("pageStatus", "editUser");
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            // when the press the "Delete" button of the user in the table
            // it passes the email address for SQL query
            case "deleteUser":
                try {
                    String key = request.getParameter("key");
                    userService.delete(key);
                    session.setAttribute("pageStatus", "display");
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            // when the press the "Save" button of the user form
            case "saveUser":
                String email = request.getParameter("email");      
                boolean isActive = ("active".equals(request.getParameter("isActive")));
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String password = request.getParameter("password");
                int role = Integer.parseInt(request.getParameter("roleName"));

                User user = new User(email, isActive, firstName, lastName, password, role);

                String pageStatus = (String) session.getAttribute("pageStatus"); // verify if adding a new user or editing the existing user
                try {
                    if ("addUser".equals(pageStatus)) { // adding a new user
                        userService.insert(user);
                    } else if ("editUser".equals(pageStatus)) { // editing the existing user
                        userService.update(user);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                session.setAttribute("pageStatus", "display");
                break;
            // when the press the "Cancel" button of the user form
            case "cancel":
                session.setAttribute("pageStatus", "display");
                break;
        }
        response.sendRedirect("User");
    }
}
