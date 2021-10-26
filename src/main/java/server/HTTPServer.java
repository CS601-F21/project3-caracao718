package server;


import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;

public class HTTPServer {
    private final int port;
    private volatile boolean running;
    private final ExecutorService threadPool;
    private int threadPoolSize = 30;

    /**
     * The constructor for creating an HTTP server
     * @param port
     */
    public HTTPServer(int port) {
        this.port = port;
        this.running = true;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * Maps a URI path to a specific Handler instance.
     */
    public void addMapping(String path, Handler handler) {
        
    }

    /**
     * Start listening for new client connections.
     */
    public void startUp() {
        // every client connection is handled by one thread
        try (ServerSocket serve = new ServerSocket(port)) {
            LOGGER.info("HTTP Server listening on port " + port);
            while (running) {
                LOGGER.info("Waiting for new client connection...");

                // use one thread from the threadpool to accept a new client connection
                threadPool.execute(new Socket());
                // LOGGER.info("New connection from " + );
            }
        }
    }
}
