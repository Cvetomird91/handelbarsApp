package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.util.function.Predicate;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProcessServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String parameter = request.getParameter("type");
        PrintWriter out = response.getWriter();

        String compiled = processParameter(parameter);

        try {
            out.println(compiled);
        } finally {
            out.close();
        }
    }

    public static String processParameter(String getParameter) throws IOException {

        String json = FilesHandler.readContentFromFile("/webapps/handlebars/WEB-INF/classes/data.json");
        String tpl = FilesHandler.readContentFromFile("/webapps/handlebars/WEB-INF/classes/list.hbs");

        Predicate<JsonNode> predicate;

        switch (getParameter) {
            case "received":
                predicate = ((JsonNode p) -> p.get("status") != null && "Received".equals(p.get("status").asText()));
                break;
            case "void":
                predicate = ((JsonNode p) -> p.get("status") != null && "voidTransaction".equals(p.get("value1").asText()));
                break;
            case "with-exceptions":
                predicate = ((JsonNode p) -> p.get("status") != null && p.get("status").asText().contains("Exception"));
                break;
            default:
                predicate = null;
                break;
        }

        return HandlebarsUtility.compile(json, tpl, predicate);
    }

}