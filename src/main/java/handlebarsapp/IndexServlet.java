package handlebarsapp;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IndexServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html");

        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/sample/index.jsp");
        requestDispatcher.forward(request, response);
    }

}
