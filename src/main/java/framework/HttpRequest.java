package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private String method;
    private String path;
    private String version;
    private static final Logger LOGGER = LogManager.getLogger(HttpRequest.class);
    private PrintWriter writer;
    private int contentLength;
    private BufferedReader instream;
    private List<String> headers;


    public HttpRequest(PrintWriter writer, BufferedReader instream) {
        this.instream = instream;
        this.writer = writer;

        String requestLine = readLine();
        LOGGER.info("HttpRequest: " + requestLine);
        String[] requestLineParts = requestLine.split("\\s");
        this.method = requestLineParts[0];
        this.path = requestLineParts[1];
        this.version = requestLineParts[2];

        LOGGER.debug("Http Method: " + method);
        LOGGER.debug("Path: " + path);
        LOGGER.debug("Http Version: " + version);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getContentLength() {
        contentLength = 0;
        headers = new ArrayList<>();
        String header;
        try {
            while (!(header = instream.readLine()).isEmpty()) {
                headers.add(header);

                // need to check if content-length header is correctly formatted
                if (header.startsWith(HttpConstants.CONTENT_LENGTH)) {
                    String[] contentLengthParts = header.split("\\s");
                    contentLength = Integer.parseInt(contentLengthParts[1]);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (contentLength == 0) {
            // bad request
            ServerUtils.send411(writer);
        }
        return contentLength;
    }

    private String readLine() {
        try {
            return instream.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public void validMethod() {
        if (!method.equals(HttpConstants.GET) && !method.equals(HttpConstants.POST)) {
            // bad request
            ServerUtils.send405(writer);
            // continue finishing the thread
        } else {
            LOGGER.info("method is valid");
        }
    }
}
