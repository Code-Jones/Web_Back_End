package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 * @author 786524
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String verification = request.getParameter("verification");
        if (verification == null || verification.equals("")) {
            getServletContext().getRequestDispatcher("/WEB-INF/ResetPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("verification", verification);
            getServletContext().getRequestDispatcher("/WEB-INF/NewPasswordReset.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        AccountService as = new AccountService();
        if (email != null && !email.isEmpty()) {
            String url = request.getRequestURL().toString();
            String path = getServletContext().getRealPath("/WEB-INF");
            as.resetPassword(email, path, url);
        } else {
            String verification = request.getParameter("verification");
            String password = request.getParameter("password");
            as.changePassword(verification, password);
        }
        response.sendRedirect("Login");
    }

}
