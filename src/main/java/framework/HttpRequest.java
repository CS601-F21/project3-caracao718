package framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that reads in the request from client
 */
public class HttpRequest {
    private String method;
    private String path;
    private String version;
    private static final Logger LOGGER = LogManager.getLogger(HttpRequest.class);
    private PrintWriter writer;
    private int contentLength;
    private BufferedReader instream;
    private List<String> headers;


    /**
     * A constructor for creating an HTTP request
     * @param writer
     * @param instream
     */
    public HttpRequest(PrintWriter writer, BufferedReader instream) {
        this.instream = instream;
        this.writer = writer;

        String requestLine = readLine();
        LOGGER.info("HttpRequest: " + requestLine);
        String[] requestLineParts = requestLine.split("\\s");
        this.method = requestLineParts[0];
        this.path = requestLineParts[1];
        this.version = requestLineParts[2];
        if (validMethod()) {
            contentLength = findContentLength();
        }

        LOGGER.debug("Http Method: " + method);
        LOGGER.debug("Path: " + path);
        LOGGER.debug("Http Version: " + version);
    }

    /**
     * A getter method to get the method
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * A getter method to get the path
     * @return
     */
    public String getPath() {
        return path;
    }

    public int getContentLength() {
        return contentLength;
    }

    /**
     * A getter method to get the content length
     * @return
     */
    private int findContentLength() {
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
        if (contentLength == 0 && Objects.equals(method, "POST")) {
            // length required
            ServerUtils.send411(writer);
            writer.println(HttpConstants.LENGTH_REQUIRED_PAGE);
            return 0;
        }
        return contentLength;
    }

    /**
     * Reads in a line of request from the client
     * @return
     */
    private String readLine() {
        try {
            return instream.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * Validate the method from client
     */
    public boolean validMethod() {
        if (!method.equals(HttpConstants.GET) && !method.equals(HttpConstants.POST)) {
            // bad request
            LOGGER.info("method is not valid");
            ServerUtils.send405(writer);
            writer.println(HttpConstants.METHOD_NOT_ALLOWED_PAGE);
            return false;
        }

        LOGGER.info("method is valid");
        return true;
    }
}
