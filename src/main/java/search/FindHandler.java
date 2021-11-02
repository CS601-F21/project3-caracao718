package search;

import framework.HTTPServer;
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
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FindHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER = LogManager.getLogger(FindHandler.class);;
    private int contentLength;
    private AmazonSearch search;

    public FindHandler(AmazonSearch search) {
        this.search = search;
    }

    @Override
    public synchronized void startApplication(PrintWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
        if (method.equals(HttpConstants.GET)) {
            doGet();
        } else if (method.equals(HttpConstants.POST)) {
            doPost();
        }
    }

    public synchronized void doGet() {
        ServerUtils.send200(writer);
        writer.println(FindConstants.GET_FIND_PAGE);
    }

    public synchronized void doPost() {
        char[] bodyArr = new char[contentLength];
        try {
            reader.read(bodyArr, 0, bodyArr.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = new String(bodyArr);
        LOGGER.info("Message body: " + body);


        // need to handle if it's not query=term return 400 if query != query
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

//        if (!Objects.equals(queryValue, ReviewSearchConstants.QUERY)) {
//            ServerUtils.send400(writer);
//        } else {
//            // do the reviewsearh in project 1, return the current results in HTML page
//            List<String> reviewSearchResults = new CopyOnWriteArrayList<>(search.getResults(ReviewSearchConstants.REVIEW_SEARCH, bodyValue));
//
//            ServerUtils.send200(writer);
//            writer.println(ReviewSearchConstants.PAGE_HEADER);
//            writer.println("<h3>Messages</h3>\n");
//            writer.println("<ul>\n");
//            for(String result: reviewSearchResults) {
//                writer.println("<li>" + result + "</li>\n");
//            }
//            writer.println("</ul>\n");
//            writer.println(ReviewSearchConstants.REVIEW_SEARCH_BODY);
//            writer.println(ReviewSearchConstants.PAGE_FOOTER);
//        }

        // do the reviewsearh in project 1, return the current results in HTML page
        List<String> findResults = new CopyOnWriteArrayList<>(search.getResults(FindConstants.FIND, bodyValue));

        ServerUtils.send200(writer);
        writer.println(FindConstants.PAGE_HEADER);
        writer.println("<h3>Messages</h3>\n");
        writer.println("<ul>\n");
        for(String result: findResults) {
            writer.println("<li>" + result + "</li>\n");
        }
        writer.println("</ul>\n");
        writer.println(FindConstants.FIND_BODY);
        writer.println(FindConstants.PAGE_FOOTER);
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
