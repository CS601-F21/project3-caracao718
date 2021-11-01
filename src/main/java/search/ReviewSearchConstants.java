package search;

import framework.HttpConstants;

public class ReviewSearchConstants {
    public static final String REVIEW_SEARCH = "/reviewsearch";

    public static final String PAGE_HEADER = HttpConstants.HTML_HEADER +
            "<head>\n" +
            "  <title>Review Search</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n";


    public static final String GET_REVIEW_SEARCH_PAGE = PAGE_HEADER +
            "<form action=\"/reviewsearch\" method=\"post\">\n" +
            "  <label for=\"msg\">Message:</label><br/>\n" +
            "  <input type=\"text\" id=\"msg\" name=\"msg\"/><br/>\n" +
            "  <input type=\"submit\" value=\"Submit\"/>\n" +
            "</form>\n" +
            HttpConstants.HTML_FOOTER;
}
