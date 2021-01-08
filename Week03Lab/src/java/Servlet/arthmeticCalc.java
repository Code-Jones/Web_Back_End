package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class arthmeticCalc extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "---");
        getServletContext().getRequestDispatcher("/WEB-INF/arthmeticCalculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String first = request.getParameter("first");
        String second = request.getParameter("second");
        String button = request.getParameter("operation");
        
        try {
            int fNumber = Integer.parseInt(first);
            int sNumber = Integer.parseInt(second);
            int nNumber = 0;
            switch (button) {
                case "+": 
                    nNumber = fNumber + sNumber;
                    break;
                case "-":
                    nNumber = fNumber - sNumber;
                    break;
                case "*":
                    nNumber = fNumber * sNumber;
                    break;
                case "%":
                    nNumber = fNumber % sNumber;
                    break;
                default:
                    request.setAttribute("message", "---");
                    break;   
            }
            
            request.setAttribute("message", String.valueOf(nNumber));
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", "Invalid");
            getServletContext().getRequestDispatcher("/WEB-INF/arthmeticCalculatorComputed.jsp").forward(request, response);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/arthmeticCalculator.jsp").forward(request, response);

    }

}
