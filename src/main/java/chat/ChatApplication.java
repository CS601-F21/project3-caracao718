package chat;

import com.google.gson.Gson;
import framework.HTTPServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * An application that send messages to slack using slackbot
 */
public class ChatApplication {
    private static final Logger LOGGER = LogManager.getLogger(ChatApplication.class);
    public static void main(String[] args) {
        String url = "https://slack.com/api/chat.postMessage";
        int port = 9090;

        Gson gson = new Gson();
        Token tokenObject = null;
        try {
            tokenObject = gson.fromJson(new FileReader("token.json"), Token.class);
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
            System.exit(1);
        }
        LOGGER.info("read in the Token");

        String token = tokenObject.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        headers.put("channel", "C02KAM114UT");
        headers.put("Content-Type", "application/json");

        HTTPServer server = new HTTPServer(port);
        server.addMapping("/slackbot", new ChatHandler(url, headers));
        server.startUp();
        LOGGER.info("Starting up");
    }
}
