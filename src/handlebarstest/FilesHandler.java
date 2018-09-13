package handlebarstest;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class FilesHandler {

    private static String json, hbs;
    private static Path outputPath;

    private static Charset charset = StandardCharsets.UTF_8;

    private static boolean checkPathReadingValidity(String toCheck) {
        Path path = Paths.get(toCheck);

        return Files.exists(path) && Files.isReadable(path) && Files.isRegularFile(path);
    }

    private static String fileToString(String toRead) {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(toRead));
            return new String(fileBytes, charset);
        } catch (Exception e) {
            return "";
        }
    }

    public static void setEncoding(Charset charset) {
        FilesHandler.charset = charset;
    }

    public static boolean handleJsonFile(String path) {
        if (!checkPathReadingValidity(path)) return false;
        json = fileToString(path);

        return !(json == null || json.isEmpty());
    }

    public static boolean handleHbsFile(String path) {
        if (!checkPathReadingValidity(path)) return false;
        hbs = fileToString(path);

        return !(hbs == null || hbs.isEmpty());
    }

    public static boolean handleOutputFile(String path) {

        FilesHandler.outputPath = Paths.get(path);

        try {
            if (Files.exists(outputPath)) {
                new PrintWriter(new File(path)).close();
            } else {
                Files.createFile(outputPath);
            }
        } catch (IOException e) {
            return false;
        }

        return Files.isWritable(outputPath) && Files.isRegularFile(outputPath);
    }

    public static boolean appendToOutputFile(String toAppend) {
        try {
            Files.write(outputPath, Collections.singleton(toAppend), charset);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getJson() {
        return json;
    }

    public static String getHbs() {
        return hbs;
    }

}
