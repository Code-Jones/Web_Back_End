package servlet;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.User;

/**
 *
 * @author 786524
 */
public class inventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDB udb = new UserDB();
        ItemDB idb = new ItemDB();
        CategoryDB cdb = new CategoryDB();
        String action = (String) request.getParameter("action");
        HttpSession session = request.getSession();
        String accountEmail = (String) session.getAttribute("email");

        List<Item> items = idb.getAllFromUser(udb.get(accountEmail));
        request.setAttribute("items", items);
        List<Category> categorgies = cdb.getAll();
        request.setAttribute("categories", categorgies);
        User user = null;

        if (accountEmail != null) {
            user = udb.get(accountEmail);
            request.setAttribute("name", (user.getFirstName() + " " + user.getLastName()));
        }

        try {
            if (action != null && action.equals("editItem")) {
                int thisItem = Integer.parseInt(request.getParameter("thisItem"));
                Item editItem = idb.get(thisItem);
                request.setAttribute("thisItem", editItem);
                request.setAttribute("heading", "Edit Item");
            } else {
                request.setAttribute("heading", "Add Item");
            }
        } catch (NumberFormatException e) {
        }

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemDB idb = new ItemDB();
        UserDB udb = new UserDB();
        CategoryDB cdb = new CategoryDB();
        HttpSession session = request.getSession();
        String action = (String) request.getParameter("action");
        String accountEmail = (String) session.getAttribute("email");
        User accountUser = udb.get(accountEmail);

        switch (action) {
            case "deleteItem":
                request.setAttribute("testing", "in delete");
                Item deleteMe = idb.get(Integer.parseInt(request.getParameter("thisItem")));
                request.setAttribute("testing", deleteMe.getItemName());
                try {
                    idb.delete(deleteMe);
                } catch (Exception e) {

                }
                action = null;
                break;
            case "addItem":
                try {
                    Category cat = cdb.get(Integer.parseInt(request.getParameter("category")));
                    double price = Double.parseDouble(request.getParameter("price"));
                    String itemName = (String) request.getParameter("itemName");
                    User user = udb.get(accountEmail);
                    idb.addItem(cat, itemName, price, user);
                    request.setAttribute("message", "New Item successfully added");
                } catch (Exception e) {
                    request.setAttribute("message", "Fill the fields pls");
                }
                action = null;
                break;
            case "saveItem":

                try {
                    Category saveCat = cdb.get(Integer.parseInt(request.getParameter("category")));
                    double price = Double.parseDouble(request.getParameter("price"));
                    String itemName = (String) request.getParameter("itemName");
                    int itemId = Integer.parseInt(request.getParameter("thisItem"));
                    idb.update(saveCat, itemId, itemName, price);
                    request.setAttribute("heading", "Add Item");
                    request.setAttribute("message", "Item Successfully edited");
                } catch (Exception e) {
                    request.setAttribute("message", "Fill the fields pls");
                } 
                action = null;
                break;
        }

        request.setAttribute("name", (accountUser.getFirstName() + " " + accountUser.getLastName()));
        List<Item> items = idb.getAllFromUser(udb.get(accountEmail));
        request.setAttribute("items", items);
        List<Category> categorgies = cdb.getAll();
        request.setAttribute("categories", categorgies);

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }
}
