package framework;

/**
 * A helper class to store various constants used for the HTTP server
 */
public class HttpConstants {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String VERSION = "HTTP/1.1";

    public static final String SHUT_DOWN = "/shutdown";

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String NOT_ALLOWED = "405 Method Not Allowed";
    public static final String LENGTH_REQUIRED = "411 Length Required";
    public static final String BAD_REQUEST = "400 Bad Request";

    public static final String CONTENT_LENGTH = "Content-Length:";
    public static final String CONNECTION_CLOSE = "Connection: close";

    public static final String NOT_FOUND_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Resource not found</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The resource you are looking for was not found.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String HTML_HEADER = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
    public static final String HTML_FOOTER = "</body>\n" +
            "</html>";

    public static final String LENGTH_REQUIRED_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Length Required</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>Content Length required in the headers.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String METHOD_NOT_ALLOWED_PAGE = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "  <title>Method not Allowed</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>Method is not allowed.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static final String SHUT_DOWN_PAGE = HTML_HEADER +
            "<head>\n" +
            "  <title>Server Stopped</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "  <p>The resource you are looking for was not found.</p>\n" +
            "\n" + HTML_FOOTER;
}
