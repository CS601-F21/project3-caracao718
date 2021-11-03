package framework;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HTTPServer {
    private final int port;
    private volatile boolean running;
    private final ExecutorService threadPool;
    private final int threadPoolSize = 30;
    private static final Logger LOGGER = LogManager.getLogger(HTTPServer.class);


    private Map<String, Handler> mappings = new HashMap<>();

     private ServerSocket serverSocket = null;

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
     * Server stop accepting new connections
     */
    public void shutDown(PrintWriter writer) {
        running = false;
//        try {
//            wait(3000);
//            writer.println(HttpConstants.SHUT_DOWN_PAGE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.exit(0);
    }

    /**
     * Maps a URI path to a specific Handler instance.
     */
    public void addMapping(String path, Handler handler) {

        this.mappings.put(path, handler);
    }

    /**
     * Start listening for new client connections. Each incoming request is handled by a different thread
     */
    public void startUp() {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info("HTTP Server listening on port: " + port);
            while (running) {
                LOGGER.info("Waiting for new client connection...");
                Socket socket = serverSocket.accept();
                LOGGER.info("New connection from " + socket.getInetAddress());
                threadPool.execute(new RunSocket(socket));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * An inner class to start a server-client connect using one socket
     */
    private class RunSocket implements Runnable {
        private final Socket socket;

        public RunSocket(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            processRequest();
        }

        private void processRequest() {
            try (
                    BufferedReader instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream())
            ) {
                HttpRequest httpRequest = new HttpRequest(writer, instream);
                httpRequest.validMethod();

                if (mappings.containsKey(httpRequest.getPath())) {
                    Handler currHandler = mappings.get(httpRequest.getPath());
                    currHandler.setPath(httpRequest.getPath());
                    currHandler.setMethod(httpRequest.getMethod());
                    currHandler.setReader(instream);
                    currHandler.setWriter(writer);
                    if (httpRequest.getMethod().equals(HttpConstants.POST)) {
                        currHandler.setContentLength(httpRequest.getContentLength());
                    } else {
                        currHandler.setContentLength(0);
                    }

                    currHandler.startApplication(writer, instream);
                } else if (httpRequest.getPath().equals(HttpConstants.SHUT_DOWN)) {
                    LOGGER.info("Shutting down the server");
                    shutDown(writer);
                } else {
                    ServerUtils.send404(writer);
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }
}
