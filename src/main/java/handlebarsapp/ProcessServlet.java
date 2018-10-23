package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProcessServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String parameter = request.getParameter("type");
        PrintWriter out = response.getWriter();

        Charset charset = StandardCharsets.UTF_8;

        String json = HandlebarsUtility.readContentFromFile("/webapps/handlebars/WEB-INF/classes/data.json", charset);
        String tpl = HandlebarsUtility.readContentFromFile("/webapps/handlebars/WEB-INF/classes/list.hbs", charset);

        String compiled = HandlebarsUtility.compile(json, tpl, (JsonNode p) -> p.get("status").asText() == null || !p.get("status").asText().equals("Received"));

        try {
            out.println(compiled);
        } finally {
            out.close();
        }
    }

}