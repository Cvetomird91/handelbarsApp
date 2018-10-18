package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProcessServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String parameter = request.getParameter("type");
        PrintWriter out = response.getWriter();

        byte[] fileBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/webapps/handlebars/WEB-INF/classes/data.json"));

        Charset charset = StandardCharsets.UTF_8;
        String json = new String(fileBytes, charset);

        JsonFiltering filter = new JsonFiltering(json);

        JsonNode rootNode = filter.getRootNode().get("data");

        switch (parameter) {
            case "received":
                ArrayNode jsonAry = (ArrayNode)rootNode;

                for (int i = 0; i < jsonAry.size(); i++) {
                    try {
                        if (jsonAry.get(i) != null && jsonAry.get(i).has("status") && !jsonAry.get(i).get("status").textValue().equals("Received")) {
                            jsonAry.remove(i);
                        }
                    } catch (NullPointerException e) {

                    }
                }

                out.println(jsonAry);
                break;
            case "void":
            case "with-exceptions":
            default:
        }

        try {

        } finally {
            out.close();
        }
    }

}
