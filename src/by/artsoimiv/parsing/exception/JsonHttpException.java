package by.artsoimiv.parsing.exception;

import by.artsoimiv.parsing.JsonFactory.JsonObject;
import by.artsoimiv.parsing.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

public class JsonHttpException extends RuntimeException {

    private JsonObject jsonError;
    private String errorContent;

    public JsonHttpException(HttpURLConnection conn) throws IOException {
        super(conn.getResponseCode() + " " + conn.getResponseMessage() + " when accessing " + conn.getURL());
        if (conn.getContentType() == null || conn.getErrorStream() == null) {
            return;
        }
        String contentType = conn.getContentType().split(";")[0].trim();
        try (InputStream error = conn.getErrorStream()) {
            if (contentType.equals("application/json")) {
                jsonError = JsonParser.parseToObject(error);
            } else {
                errorContent = asString(error);
            }
        }
    }

    private String asString(InputStream error) throws IOException {
        try (Reader reader = new InputStreamReader(error)) {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[1024*1024];
            int c;
            while ((c = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, c);
            }
            return builder.toString();
        }
    }

    public JsonObject getJsonError() {
        return jsonError;
    }

    public String getErrorContent() {
        return errorContent;
    }

}
