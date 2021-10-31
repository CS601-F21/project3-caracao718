package search;

import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ReviewSearchHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER;
    private int contentLength;

    public void startApplication() {
        if (method.equals(HttpConstants.GET)) {
            doGet();
        } else if (method.equals(HttpConstants.POST)) {
            doPost();
        }
    }

    public void doGet() {
        ServerUtils.send200(writer);
        // send the HTML page
        writer.println(ReviewSearchConstants.GET_REVIEW_SEARCH_PAGE);
    }

    private void doPost() {
        char[] bodyArr = new char[contentLength];
        try {
            reader.read(bodyArr, 0, bodyArr.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = new String(bodyArr);
        LOGGER.info("Message body: " + body);
    }

    @Override
    public void handle(Handler handler) {

    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void setLogger(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    @Override
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
}
