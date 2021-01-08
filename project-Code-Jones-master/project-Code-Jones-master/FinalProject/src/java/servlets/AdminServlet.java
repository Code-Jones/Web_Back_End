package servlets;

import dataaccess.CategoryDB;
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
import models.Category;
import models.Role;
import models.User;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // prep
            HttpSession session = request.getSession();
            UserDB udb = new UserDB();
            CategoryDB cdb = new CategoryDB();
            String action = (String) request.getParameter("action");
            String accountEmail = (String) session.getAttribute("email");

            // load lists
            List<User> users = udb.getAll();
            request.setAttribute("users", users);
            List<Category> categories = cdb.getAll();
            request.setAttribute("categories", categories);

            // set account name
            if (accountEmail != null) {
                User name = udb.get(accountEmail);
                request.setAttribute("name", (name.getFirstName() + " " + name.getLastName()));
            }

            // check action 
            try {
                switch (action) {
                    case "editUser":
                        String thisUser = (String) request.getParameter("thisUser");
                        User editUser = udb.get(thisUser);
                        request.setAttribute("thisUser", editUser);
                        request.setAttribute("heading", "Edit User");
                        request.setAttribute("catheading", "Add Category");
                        break;
                    case "editCat":
                        String thisCatIdStr = (String) request.getParameter("thisCat");
                        int thisCatId = Integer.valueOf(thisCatIdStr);
                        Category thisCat = cdb.get(thisCatId);
                        session.setAttribute("test", thisCatIdStr);
                        request.setAttribute("thisCat", thisCat);
                        request.setAttribute("heading", "Add User");
                        request.setAttribute("catheading", "Edit Category");
                        break;
                    default:
                        request.setAttribute("heading", "Add User");
                        request.setAttribute("catheading", "Add Category");
                        break;
                }
            } catch (Exception e) {
                action = null;
                request.setAttribute("heading", "Add User");
                request.setAttribute("catheading", "Add Category");
            }
        } catch (Exception e) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, "this is not working", e);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // prep
        UserDB udb = new UserDB();
        CategoryDB cdb = new CategoryDB();

        // collect
        String action = (String) request.getParameter("action");

        try {
            if (action.equals("addUser") || action.equals("deleteUser") || action.equals("save")) {
                switchUser(request, action, udb);
            } else if (action.equals("addCat") || action.equals("deleteCat") || action.equals("saveCat")) {
                switchCat(request, action, cdb);
            }
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            Logger.getLogger(AdminServlet.class.getName()).log(Level.WARNING, null, e);
        }
        request.setAttribute("thisUser", null);
        request.setAttribute("thisCat", null);

        // update lists
        List<User> users = udb.getAll();
        request.setAttribute("users", users);
        List<Category> categories = cdb.getAll();
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/WEB-INF/Admin.jsp").forward(request, response);
    }

    public void switchUser(HttpServletRequest request, String action, UserDB udb) {
        try {
            String email = (String) request.getParameter("email");
            String fname = (String) request.getParameter("fname");
            String lname = (String) request.getParameter("lname");
            String password = (String) request.getParameter("password");
            User user = new User(email, true, fname, lname, password);
            Role role = new Role(2);
            user.setRole(role);
            switch (action) {
                case "addUser":
                    if (udb.addUser(user)) {
                        request.setAttribute("message", "New User successfully added");
                    } else {
                        request.setAttribute("message", "New User could not be added");
                    }
                    break;
                case "deleteUser":
                    HttpSession session = request.getSession();
                    String sessionEmail = (String) session.getAttribute("email");
                    String thisUser = (String) request.getParameter("thisUser");
                    User deleteMe = udb.get(thisUser);
                    udb.delete(deleteMe, sessionEmail);
                    break;
                case "save":
                    request.setAttribute("heading", "Add User");
                    request.setAttribute("message", "User Successfully edited");
                    udb.update(user);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("message", "Something went wrong");
            Logger.getLogger(AdminServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }

    private void switchCat(HttpServletRequest request, String action, CategoryDB cdb) {
        try {
//            HttpSession session = request.getSession();

            switch (action) {
                case "addCat":
                    String addCatName = request.getParameter("addCategoryName");
                    Category cat = new Category(0, addCatName);
                    if (cdb.addCat(cat)) {
                        request.setAttribute("message", "New Category successfully added");
                    } else {
                        request.setAttribute("message", "New Category could not be added");
                    }
                    break;
//                case "deleteCat":
//                    Category deleteCat = cdb.get(id);
//                    if (cdb.deleteCat(deleteCat)) {
//                        request.setAttribute("message", "Category successfully deleted");
//                    } else {
//                        request.setAttribute("message", "Category could not be deleted");
//                    }
//                    break;
                case "saveCat":
                    String catName = request.getParameter("editCategoryName");
                    String idStr = request.getParameter("editCatId");
                    int id = Integer.valueOf(idStr);
                    Category editCat = cdb.get(id);
                    editCat.setCategoryName(catName);
                    cdb.updateCat(editCat);
//                    if () {
//                        request.setAttribute("message", "Category successfully edited");
//                    } else {
//                        request.setAttribute("message", "Category could not be edited");
//                    }
                    break;
                default:
                    request.setAttribute("message", "hit defualt");
                    break;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Data conversion went wrong");
            Logger.getLogger(AdminServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }
}
