package search;

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private String method;
    private String path;
    private String version;
    private Logger LOGGER;
    private int contentLength;
    private BufferedReader instream;
    private List<String> headers;


    public Request(Logger logger, BufferedReader instream) {
        this.instream = instream;
        this.LOGGER = logger;

        String requestLine = readLine();
        LOGGER.info("Request: " + requestLine);
        String[] requestLineParts = requestLine.split("\\s");
        this.method = requestLineParts[0];
        this.path = requestLineParts[1];
        this.version = requestLineParts[2];

        LOGGER.debug("Http Method: " + method);
        LOGGER.debug("Path: " + path);
        LOGGER.debug("Http Version: " + version);
    }

    public int getContentLength() {
        int contentLength = 0;
        headers = new ArrayList<>();
        String header;
        try {
            while (!(header = instream.readLine()).isEmpty()) {
                headers.add(header);

                // need to check if content-length header is correctly formatted
                if (header.startsWith("CONTENT_LENGTH")) {
                    String[] contentLengthParts = header.split("\\s");
                    contentLength = Integer.parseInt(contentLengthParts[1]);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (contentLength == 0) {
            // bad request
            // finish job send response
            return -1;
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
        if (!method.equals("GET") && !method.equals("POST")) {
            // bad request
            // stop thread
        } else {
            LOGGER.info("method is valid");
        }
    }
}
