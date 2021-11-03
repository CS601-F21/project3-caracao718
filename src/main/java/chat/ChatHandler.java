package chat;

import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.jmx.Server;
import search.FindConstants;
import utils.HandlerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;


public class ChatHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER = LogManager.getLogger(ChatHandler.class);;
    private int contentLength;
    private String url;
    private Map<String, String> headers;

    public ChatHandler(String URL, Map<String, String> headers) {
        this.url = URL;
        this.headers = headers;
    }

    public synchronized void doGet() {
        ServerUtils.send200(writer);
        writer.println(ChatConstants.GET_CHAT_PAGE);
    }

    public synchronized String doPost() {
        String[] values = HandlerUtils.readInput(contentLength, reader);
        String queryValue = values[1];
        String bodyValue = values[0];

        if (!Objects.equals(queryValue, ChatConstants.QUERY)) {
            ServerUtils.send400(writer);
        }

        JsonConfig config = new JsonConfig(bodyValue);
        LOGGER.info("json message: " + config.toString());
        HTTPFetcher fetcher = new HTTPFetcher(config.toString(), url, headers);
        String response = fetcher.doPost();
        LOGGER.info("message sent to slack, page should show another textbox");

        // if success, else send status
        if (postIsValid(response)) {
            ServerUtils.send200(writer);
            writer.println(ChatConstants.GET_CHAT_PAGE);
        } else {
            ServerUtils.send400(writer);
        }

        System.out.println(response);
        return response;
    }

    /**
     * Validate if the response from Slack is success
     * @param response
     * @return
     */
    private synchronized boolean postIsValid(String response) {
        char validationChar = response.charAt(6);
        return validationChar == 't';
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
