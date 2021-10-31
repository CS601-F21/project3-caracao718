package search;

import framework.Handler;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class FindHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER;
    private int contentLength;

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
