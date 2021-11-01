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

public class ReviewSearchHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private static final Logger LOGGER = LogManager.getLogger(ReviewSearchHandler.class);
    private int contentLength;
    private AmazonSearch search;

    private List<String> reviewSearchResults = new ArrayList<>();

    public ReviewSearchHandler(AmazonSearch search) {
        this.search = search;
    }

    public void startApplication() {
        if (method.equals(HttpConstants.GET)) {
            doGet();
        } else if (method.equals(HttpConstants.POST)) {
            doPost();
        }
    }

    public void doGet() {
        System.out.println("doing get");
        ServerUtils.send200(writer);
        // send the HTML page
        writer.println(ReviewSearchConstants.GET_REVIEW_SEARCH_PAGE);
    }

    private void doPost() {
        System.out.println("do post");
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
        try {
            bodyValue = URLDecoder.decode(body.substring(body.indexOf("=")+1, body.length()), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Message body value: " + bodyValue);
        System.out.println(bodyValue);

        String[] input = bodyValue.split("=");
        if (!input[0].equals("query")) {
            ServerUtils.send400(writer);
        } else {
            bodyValue = input[1];
        }
        System.out.println(bodyValue);


        // do the reviewsearh in project 1, return all the results in the HTML page
//        AmazonSearch reviewSearch = new AmazonSearch("reviewsearch", bodyValue);
        reviewSearchResults = search.getResults("reviewsearch", bodyValue);

//        reviewSearchResults.add("hi");
//        reviewSearchResults.add("hi");
//        reviewSearchResults.add("hi");
//        reviewSearchResults.add("hi");
//        reviewSearchResults.add("hi");
        

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
