package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;


public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");

        if (uuid != null) {
            request.setAttribute("uuid", uuid);
            this.getServletContext().getRequestDispatcher("/WEB-INF/afterReset.jsp").forward(request, response);
            return;
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        AccountService as = new AccountService();

        switch (action) {
            case "requestreset":
                String email = request.getParameter("email");
                String path = this.getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                as.resetPassword(email, path, url);
                String message = "If the address you entered is valid, you will receive an email very soon." 
                        + " Please check your email for your password.";
                request.setAttribute("message", message);
                this.getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                break;
            case "setpassword":
                String newPassword = request.getParameter("newpassword");
                String uuid = request.getParameter("uuid");
                boolean changed = as.changePassword(uuid, newPassword);
                request.setAttribute("changed", changed);
                this.getServletContext().getRequestDispatcher("/WEB-INF/afterReset.jsp").forward(request, response);
        }
        
    }
}