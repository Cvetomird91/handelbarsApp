package handlebarsapp;

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

        Charset charset = StandardCharsets.UTF_8;

        String json = readContentFromFile("/webapps/handlebars/WEB-INF/classes/data.json", charset);
        String tpl = readContentFromFile("/webapps/handlebars/WEB-INF/classes/list.hbs", charset);

        String compiled = HandlebarsUtility.compile(json, tpl);

        try {
            out.println(compiled);
        } finally {
            out.close();
        }
    }

    private static String readContentFromFile(String path, Charset charset) throws IOException {
        byte[] rawBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + path));
        return new String(rawBytes, charset);
    }

}
