package chat;

import com.google.gson.Gson;
import framework.HTTPServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ChatApplication {

    public static void main(String[] args) {
        String url = "https://slack.com/api/chat.postMessage";
        Gson gson = new Gson();

        Token tokenObject = null;
        try {
            tokenObject = gson.fromJson(new FileReader("token.json"), Token.class);
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
            System.exit(1);
        }
        int port = 1024;

        String token = tokenObject.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        HTTPFetcher getChannelID = new HTTPFetcher();
        String response = getChannelID.doGet("https://slack.com/api/conversations.list", headers);
        System.out.println(response);
        headers.put("channel", "C02KAM114UT");
        headers.put("Content-Type", "application/json");

        HTTPServer server = new HTTPServer(port);
        server.addMapping("/slackbot", new ChatHandler(url, headers));
        server.startUp();

    }
}
