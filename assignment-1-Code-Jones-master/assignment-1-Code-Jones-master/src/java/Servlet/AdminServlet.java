package Servlet;

import Models.AccountService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService acct = (AccountService) session.getAttribute("acct");
        String logout = request.getParameter("logout");
        String path = getServletContext().getRealPath("/WEB-INF/homeitems.txt");
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line, owner = null, item = null;
        int expensive = 0, total = 0;

        if (acct == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
        } else {
             if (!(acct.getUsername().equals("admin"))) {
                 response.sendRedirect(request.getContextPath() + "/inventory");
                 return;
             }
        }

        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            total += Integer.valueOf(lines[3]);
            if (Integer.valueOf(lines[3]) > expensive) {
                owner = lines[0];
                item = lines[1];
                expensive = Integer.valueOf(lines[3]);
            }
        }
        request.setAttribute("total", total);
        request.setAttribute("item", item);
        request.setAttribute("expensive", expensive);
        request.setAttribute("owner", owner);
        if (logout != null) {
            acct.logout();
            request.getSession().invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }

}
