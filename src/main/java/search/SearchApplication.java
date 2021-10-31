package search;

import framework.HTTPServer;
import framework.HttpConstants;
import framework.ServerUtils;

public class SearchApplication {

    public static void main(String[] args) {
        String path = "/reviewsearch";
        String method = "GET";
        int port = 8080;
        HTTPServer server = new HTTPServer(port);
        server.addMapping(path, new ReviewSearchHandler());
        server.startUp();
    }
}
