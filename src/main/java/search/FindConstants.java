package search;

import framework.HttpConstants;

/**
 * A helper class to store various constants used for the HTTP server.
 */
public class FindConstants {
    public static final String FIND = "find";
    public static final String QUERY = "msg";

    public static final String PAGE_HEADER = HttpConstants.HTML_HEADER +
            "<head>\n" +
            "  <title>Find</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";

    public static final String PAGE_FOOTER = "\n" +
            "</body>\n" +
            "</html>";

    public static final String GET_FIND_PAGE = PAGE_HEADER +
            "<form action=\"/find\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String RETURN_EMPTY_FIND_PAGE = PAGE_HEADER +
            "<form action=\"/find\" method=\"post\">\n" +
            "  <label for=\"msg\">try again:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            PAGE_FOOTER;

    public static final String FIND_BODY = "<form action=\"/find\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n";
}
