package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/*
    doGet: get "action" parameter and display user.jsp
    doPost: get "action" parameter and add, edit, delete, save cancel using case statement
 */
/**
 * Servlet responsible for all processing on web page
 */
public class UserServlet extends HttpServlet {

    /**
     * Gets the "action" and displays the user.jsp based on status Populates
     * List for users
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService();

        // change user form in 'user.jsp' as per the the diplay mode such as default, add or edit user
        String action = request.getParameter("action");

        if (null == action || "cancel".equals(action)) { // default display or when the press the "Cancel" button of the user form
            defaultDisplay(request);
        } else {
            request.setAttribute("enableForm", true);
            request.setAttribute("cancelForm", true);
            switch (action) {
                case "addUser": // when the press the "Add User" button of the user form                
                    request.setAttribute("addUser", true);
                    break;
                case "editUser": // when the press the "Edit" button of the user in the table                
                    try {
                        String email = request.getParameter("email");
                        User user = userService.get(email);
                        request.setAttribute("userToEdit", user);
                        request.setAttribute("editUser", true);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }
        setLists(request, userService);
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    /**
     * Responsible for all actions performed on Users Based off of action
     * parameter it can: add a user, edit a user, delete a user or save new user
     * info
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService();
        String action = request.getParameter("action");

        switch (action) {
            // when the press the "Delete" button of the user in the table
            // it passes the email address for SQL query
            case "deleteUser":
                try {
                    String email = request.getParameter("email");
                    userService.delete(email);
                    request.setAttribute("deleteMsg", true);
                    request.setAttribute("emailDeleted", email);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
            // when the press the "Save" button of the user form
            case "saveUser":
                String email = request.getParameter("email");
                boolean isActive = ("active".equals(request.getParameter("isActive")));
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String password = request.getParameter("password");
                int roleId = Integer.parseInt(request.getParameter("roleName"));

                if (email == null || email.equals("")) { // email is mandatory to add a new user
                    request.setAttribute("invalidUser", true);
                    break;
                }

                User user = new User(email, isActive, firstName, lastName, password);
                user.setRoleId(new Role(roleId));

                String saveMode = request.getParameter("saveMode");
                try {
                    if ("addUser".equals(saveMode)) { // adding a new user
                        userService.insert(user);
                        request.setAttribute("addMsg", true);
                        request.setAttribute("emailAdded", user.getEmail());
                    } else if ("editUser".equals(saveMode)) { // editing the existing user
                        userService.update(user);
                        request.setAttribute("editMsg", true);
                        request.setAttribute("emailedited", user.getEmail());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        defaultDisplay(request);
        setLists(request, userService);
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    /**
     * Initial display and when pressed the cancel button.
     */
    private HttpServletRequest defaultDisplay(HttpServletRequest request) {
        request.setAttribute("defaultTitle", true);
        request.setAttribute("enableForm", false);
        request.setAttribute("cancelForm", false);
        return request;
    }

    /**
     * Pass users and roles to user.jsp
     */
    private HttpServletRequest setLists(HttpServletRequest request, UserService userService) {
        try {
            RoleService roleService = new RoleService();
            List<User> users = userService.getAll();
            List<Role> roles = roleService.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return request;
    }
}
