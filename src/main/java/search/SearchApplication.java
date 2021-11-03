package search;

import framework.HTTPServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project1.AmazonSearch;

public class SearchApplication {
    private static final Logger LOGGER = LogManager.getLogger(SearchApplication.class);
    public static void main(String[] args) {
        AmazonSearch search = new AmazonSearch();

        int port = 1024;
        HTTPServer server = new HTTPServer(port);
        LOGGER.info("listening");
        server.addMapping("/find", new FindHandler(search));
        server.addMapping("/reviewsearch", new ReviewSearchHandler(search));
        server.startUp();
        LOGGER.info("Starting up");
    }
}
