package servlets;

import com.sun.istack.internal.logging.Logger;
import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDB udb = new UserDB();
        ItemDB idb = new ItemDB();
        CategoryDB cdb = new CategoryDB();
        String action = (String) request.getParameter("action");
        HttpSession session = request.getSession();
        String accountEmail = (String) session.getAttribute("email");
        List<Item> items = new ArrayList<>(idb.getAll());

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
                String id = String.valueOf(thisItem);
                session.setAttribute("id", id);
            } else {
                request.setAttribute("heading", "Add Item");
            }
        } catch (NumberFormatException e) {
        }

        getServletContext().getRequestDispatcher("/WEB-INF/Inventory.jsp").forward(request, response);
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

        try {
            actionSwitch(request, response, action, idb, udb, cdb, accountUser);
        } catch (Exception e) {
            request.setAttribute("message", "Whoops, something went wrong.. Don't judge me too hard Aaron");
        }

        request.setAttribute("name", (accountUser.getFirstName() + " " + accountUser.getLastName()));
        List<Item> items = new ArrayList<>(idb.getAll());
        request.setAttribute("items", items);
        List<Category> categorgies = new ArrayList<>(cdb.getAll());
        request.setAttribute("categories", categorgies);
        getServletContext().getRequestDispatcher("/WEB-INF/Inventory.jsp").forward(request, response);
    }

    private void actionSwitch(HttpServletRequest request, HttpServletResponse response, String action, ItemDB idb, UserDB udb, CategoryDB cdb, User accountUser) throws ServletException, IOException {
        switch (action) {
            case "deleteItem":
                Item deleteMe = idb.get(Integer.parseInt(request.getParameter("thisItem")));
                request.setAttribute("message", "Deleted " + deleteMe.getItemName());
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
                    Item item = new Item();
                    item.setCategory(cat);
                    item.setItemName(itemName);
                    item.setPrice(price);
                    item.setOwner(accountUser);
                    idb.insert(item);
                    request.setAttribute("message", "New Item successfully added");
                } catch (Exception e) {
                    request.setAttribute("message", "Fill the fields please");
                }
                action = null;
                break;
            case "saveItem":
                try {
                    Category cat = cdb.get(Integer.parseInt(request.getParameter("category")));
                    double price = Double.parseDouble(request.getParameter("price"));
                    String itemName = (String) request.getParameter("itemName");

                    try {
                        HttpSession session = request.getSession();
                        String test = (String) session.getAttribute("id");
                        int id = Integer.valueOf(test);
                        Item item = idb.get(id);
                        item.setOwner(accountUser);
                        item.setCategory(cat);
                        item.setPrice(price);
                        item.setItemName(itemName);

                        if (item.getOwner().equals(accountUser)) {
//                        if (item.getOwner().getEmail().equals(accountUser.getEmail())) {
                            idb.update(item);
                            request.setAttribute("heading", "Add Item");
                            request.setAttribute("message", "Item Successfully Edited");
                        } else {
                            request.setAttribute("message", "You cannot edit other users inventory");
                        }
                    } catch (Exception e) {
                        request.setAttribute("message", "Failed to update item");
                    }

                } catch (Exception e) {
                    Logger.getLogger(InventoryServlet.class.getClass()).log(Level.SEVERE, "Work god damn it", e);
                    request.setAttribute("heading", "Add Item");
                    request.setAttribute("message", "Fill the fields please, try again");
                }

                action = null;
                break;
        }
    }
}
