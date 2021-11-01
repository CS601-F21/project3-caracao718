package search;

import framework.HTTPServer;
import framework.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class FindHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER = LogManager.getLogger(FindHandler.class);;
    private int contentLength;

    @Override
    public void startApplication() {

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
