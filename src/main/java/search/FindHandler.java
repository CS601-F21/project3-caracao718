package search;

import framework.HTTPServer;
import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project1.AmazonSearch;
import utils.HandlerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
        String[] values = HandlerUtils.readInput(contentLength, reader);
        String queryValue = values[1];
        String bodyValue = values[0];
        if (!Objects.equals(queryValue, FindConstants.QUERY)) {
            ServerUtils.send400(writer);
        } else {
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
