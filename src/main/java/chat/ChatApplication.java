package chat;

import framework.HTTPServer;
//import com.slack.api.bolt.App;
//import com.slack.api.bolt.AppConfig;
//import com.slack.api.bolt.jetty.SlackAppServer;
//import com.slack.api.methods.SlackApiException;

public class ChatApplication {
    public static String webHookUrl = "https://hooks.slack.com/services/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";
    public static String URL = "https://slack.com/api/chat.postMessage?channel=cs601-project3";
    public static String Token = "xoxb-2464212157-2674739062835-sgChwjJ1tMkw9zPF6M5uONMj";
    public static String slackChannel = "cs601-project3";

    public static void main(String[] args) {
        int port = 1024;
        HTTPServer server = new HTTPServer(port);
        server.addMapping("/chat", new ChatHandler(port, URL));
        server.startUp();
    }
}
