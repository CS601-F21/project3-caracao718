package chat;

import com.google.gson.Gson;
import framework.Handler;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import search.FindConstants;
import search.FindHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;

public class ChatHandler implements Handler {
    private String method;
    private String path;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger LOGGER = LogManager.getLogger(ChatHandler.class);;
    private int contentLength;
    private JsonConfig config;
    private int port;
    private String URL;

    public ChatHandler(int port, String URL) {
        this.port = port;
        this.URL = URL;
    }

    public synchronized void doGet() {
        ServerUtils.send200(writer);
        writer.println(ChatConstants.GET_CHAT_PAGE);
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

        // post bodyValue to slack
        Gson gson = new Gson();
//        config = gson.fromJson(bodyValue, JsonConfig.class);
        config = new JsonConfig(bodyValue);
        System.out.println(config.toString());

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.of(new InetSocketAddress(URL, port)))
                .authenticator(Authenticator.getDefault())
                .build();
//        HttpResponse<String> response = client.send(bodyValue, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.statusCode());
//        System.out.println(response.body());


        LOGGER.info("JSON file created");
        writer.println(ChatConstants.POST_MESSAGE_HEADER);
        writer.println(config.toString());
        LOGGER.info("Message post to slack");

        // then return the same page ready for another get
        ServerUtils.send200(writer);
        writer.println(ChatConstants.GET_CHAT_PAGE);
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
