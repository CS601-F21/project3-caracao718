package search;

import framework.HTTPServer;
import framework.HttpConstants;
import framework.ServerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project1.AmazonSearch;

public class SearchApplication {
    private static Logger LOGGER = LogManager.getLogger(SearchApplication.class);
    public static void main(String[] args) {
        AmazonSearch search = new AmazonSearch();
        String path = "/reviewsearch";
        int port = 1024;
        HTTPServer server = new HTTPServer(port);
        LOGGER.info("listening");
        server.addMapping(path, new ReviewSearchHandler(search));
        server.startUp();
        LOGGER.info("Starting up");
    }
}
