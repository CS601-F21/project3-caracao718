package chat;

import framework.HttpConstants;

/**
 * A helper class to store various constants used for the HTTP server.
 */
public class ChatConstants {
    public static final String CHAT = "/slackbot";

    public static final String QUERY = "msg";

    public static final String PAGE_HEADER = HttpConstants.HTML_HEADER +
            "<head>\n" +
            "  <title>Slack Bot</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String GET_CHAT_PAGE = PAGE_HEADER +
            "<form action=\"/slackbot\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String GET_ERROR_PAGE = PAGE_HEADER +
            "<h3>Something went wrong, please try again</h3>\n" +
            "<form action=\"/slackbot\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;
    public static final String RETURN_EMPTY_CHAT_PAGE = PAGE_HEADER +
            "<form action=\"/slackbot\" method=\"post\">\n" +
            "  <label for=\"msg\">try again:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String CHAT_BODY = "<form action=\"/slackbot\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n";

}
