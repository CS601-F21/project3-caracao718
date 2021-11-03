package chat;

import framework.HttpConstants;

public class ChatConstants {
    public static final String CHAT = "/chat";
    public static final String TOKEN = "xoxb-2464212157-2674739062835-sgChwjJ1tMkw9zPF6M5uONMj";
    public static final String QUERY = "query";

    public static final String PAGE_HEADER = HttpConstants.HTML_HEADER +
            "<head>\n" +
            "  <title>Chat</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String GET_CHAT_PAGE = PAGE_HEADER +
            "<form action=\"/chat\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String POST_MESSAGE_HEADER = "POST " +
            "/https://slack.com/api/conversations.create" + "\n" +
            "Content-type: application/json'\n" +
            "Authorization: Bearer " + TOKEN + "\n";
//            "name: cs601-project3\n";

    public static final String RETURN_EMPTY_CHAT_PAGE = PAGE_HEADER +
            "<form action=\"/chat\" method=\"post\">\n" +
            "  <label for=\"msg\">try again:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String CHAT_BODY = "<form action=\"/chat\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n";

}
