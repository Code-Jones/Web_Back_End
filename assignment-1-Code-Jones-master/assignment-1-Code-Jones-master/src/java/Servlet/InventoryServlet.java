package Servlet;

import Models.AccountService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService acct = (AccountService) session.getAttribute("acct");
        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt");
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;
        String logout = request.getParameter("logout");
        int total = 0;

        if (acct == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // gets the total for the current user
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            if (lines[0].equals(acct.getUsername())) {
                total += Integer.valueOf(lines[3]);
            }
        }

        request.setAttribute("total", total);
        if (logout != null) {
            acct.logout();
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        AccountService acct = (AccountService) session.getAttribute("acct");
        String line;
        int total = 0, price = 0;
        String priceStr;
        String category = request.getParameter("category");
        String item = request.getParameter("item");
        String username = (String) acct.getUsername();
        priceStr = request.getParameter("price");
        price = Integer.parseInt(priceStr);
        
        

        
        if (price <= 0 || price > 10000 || item.equals("") || category.equals("")) {
            request.setAttribute("message", "Invalid entry");
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        } else {
            pw.write(username + "," + category + "," + item + "," + price + "\n");
            pw.flush();
            pw.close();
            // gets the total for the current user
            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                if (lines[0].equals(acct.getUsername())) {
                    total += Integer.valueOf(lines[3]);
                }
            }
            request.setAttribute("total", total);
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        }
    }
}
