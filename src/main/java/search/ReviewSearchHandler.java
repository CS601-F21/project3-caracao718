package search;

import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReviewSearchHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private final Logger LOGGER = LogManager.getLogger(ReviewSearchHandler.class);
    private int contentLength;

    private List<String> reviewSearchResults = new ArrayList<>();

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

        String bodyValue = null;
        try {
            bodyValue = URLDecoder.decode(body.substring(body.indexOf("=")+1, body.length()), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Message body value: " + bodyValue);

        // do the reviewsearh in project 1, return all the results in the HTML page
        reviewSearchResults.add("hi");
        reviewSearchResults.add("hi");
        reviewSearchResults.add("hi");
        reviewSearchResults.add("hi");
        reviewSearchResults.add("hi");
        reviewSearchResults.add("hi");


        ServerUtils.send200(writer);
        writer.println(ReviewSearchConstants.PAGE_HEADER);
        writer.println("<h3>Messages</h3>\n");
        writer.println("<ul>\n");
        for(String result: reviewSearchResults) {
            writer.println("<li>" + result + "</li>");
        }
        writer.println("</ul>\n");
        writer.println(HttpConstants.HTML_FOOTER);
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
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
}
