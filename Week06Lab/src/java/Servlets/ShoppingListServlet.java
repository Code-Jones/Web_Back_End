package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShoppingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username;
        
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            session.invalidate();
            request.setAttribute("logout", true);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        if ((String) session.getAttribute("username") != null ) {
            username = (String) session.getAttribute("username");
            request.setAttribute("username", username);
            session.setAttribute("username", username);
            getServletContext().getRequestDispatcher("/WEB-INF/shopList.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        ArrayList<String> items = new ArrayList<>();
        String item;
        
        switch (action) {
            case "register":
                if (request.getParameter("username").equals("") || request.getParameter("username") == null) {
                    response.sendRedirect("ShoppingList");
                } else {
                    String username = request.getParameter("username");
                    session.setAttribute("username", username);
                    session.setAttribute("items", items);
                    response.sendRedirect("ShoppingList");
                }
                break;
            case "add":
                if (request.getParameter("item").equals("") || request.getParameter("item") == null) {
                    response.sendRedirect("ShoppingList");
                } else {
                    item = request.getParameter("item");
                    items = (ArrayList<String>) session.getAttribute("items");
                    items.add(item);
                    session.setAttribute("items", items);
                    response.sendRedirect("ShoppingList");
                }
                break;
            case "delete":
                item = request.getParameter("item");
                items = (ArrayList<String>) session.getAttribute("items");
                items.remove(item);
                session.setAttribute("items", items);
                response.sendRedirect("ShoppingList");
                break;
        }

    }
}
