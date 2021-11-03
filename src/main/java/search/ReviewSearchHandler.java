package search;

import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project1.AmazonSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReviewSearchHandler implements Handler {
    private String method;
    private BufferedReader reader;
    private PrintWriter writer;
    private String path;
    private static final Logger LOGGER = LogManager.getLogger(ReviewSearchHandler.class);
    private int contentLength;
    private final AmazonSearch search;

    /**
     * Constructor for ReviewSearchHandler
     * @param search
     */
    public ReviewSearchHandler(AmazonSearch search) {
        this.search = search;
    }

    /**
     * Start the Review Search
     * @param writer
     * @param reader
     */
    public synchronized void startApplication(PrintWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
        if (method.equals(HttpConstants.GET)) {
            doGet();
        } else if (method.equals(HttpConstants.POST)) {
            doPost();
        }
    }

    /**
     * Do a GET method
     */
    public synchronized void doGet() {
        ServerUtils.send200(writer);
        // send the HTML page
        writer.println(ReviewSearchConstants.GET_REVIEW_SEARCH_PAGE);
    }

    /**
     * Do a POST method
     */
    private synchronized void doPost() {

        char[] bodyArr = new char[contentLength];
        try {
            reader.read(bodyArr, 0, bodyArr.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = new String(bodyArr);
        LOGGER.info("Message body: " + body);


        String bodyValue = null;
        String queryValue = null;
        try {
            bodyValue = URLDecoder.decode(body.substring(body.indexOf("=")+1, body.length()), StandardCharsets.UTF_8.toString());
            queryValue = URLDecoder.decode(body.substring(0, body.indexOf("=")), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Message body value: " + bodyValue);
        LOGGER.info("Message query: " + queryValue);

        if (!Objects.equals(queryValue, ReviewSearchConstants.QUERY)) {
            ServerUtils.send400(writer);
        } else {
            List<String> reviewSearchResults = new CopyOnWriteArrayList<>(search.getResults(ReviewSearchConstants.REVIEW_SEARCH, bodyValue));

            ServerUtils.send200(writer);
            writer.println(ReviewSearchConstants.PAGE_HEADER);
            writer.println("<h3>Messages</h3>\n");
            writer.println("<ul>\n");
            for(String result: reviewSearchResults) {
                writer.println("<li>" + result + "</li>\n");
            }
            writer.println("</ul>\n");
            writer.println(ReviewSearchConstants.REVIEW_SEARCH_BODY);
            writer.println(ReviewSearchConstants.PAGE_FOOTER);
        }

    }


    @Override
    public synchronized void setPath(String path) {
        this.path = path;
    }

    @Override
    public synchronized void setMethod(String method) {
        this.method = method;
    }

    @Override
    public synchronized void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public synchronized void setWriter(PrintWriter writer) {
        this.writer = writer;
    }



    @Override
    public synchronized void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
}
