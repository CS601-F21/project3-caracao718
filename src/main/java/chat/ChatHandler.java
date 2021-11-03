package chat;

import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class ChatHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER = LogManager.getLogger(ChatHandler.class);;
    private int contentLength;
    private JsonConfig config;
    private int port;
    private String url;
    private Map<String, String> headers;

    public ChatHandler(int port, String URL, Map<String, String> headers) {
        this.port = port;
        this.url = URL;
        this.headers = headers;
    }


    private synchronized String readInput() {
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

        return bodyValue;
    }

    public synchronized void doGet() {
        ServerUtils.send200(writer);
        writer.println(ChatConstants.GET_CHAT_PAGE);
    }

    public synchronized String doPost() {
        String bodyValue = readInput();
        HTTPFetcher fetcher = new HTTPFetcher(bodyValue, url, headers);
        System.out.println(headers);
        String response = fetcher.doPost();

        // if success, else send status
        writer.println(ChatConstants.GET_CHAT_PAGE);

        return response;
    }

    @Override
    public void startApplication(PrintWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
        if (method.equals(HttpConstants.GET)) {
            doGet();
        } else if (method.equals(HttpConstants.POST)) {
            doPost();
        }
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
