package framework;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
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
    public void shutDown() {
        running = false;
    }

    /**
     * Maps a URI path to a specific Handler instance.
     */
    public void addMapping(String path, Handler handler) {
        // should there be a field to store path?
        // since the server must be flexible enough to support different web apps, we cannot do something like:

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
                HttpRequest httpRequest = new HttpRequest(LOGGER, writer, instream);
                httpRequest.validMethod();


                // get the content length
                int contentLength = httpRequest.getContentLength();

                // if method == get, then return a xhtml file. Send to handler -> html file
                    // if file is not already in the serve, then return 404 file not found
                    // if the file is already in server, then return the file
                // if method == post, create new XHTML and return


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }

        // a class similar to
        private void doGet() {
            // send to handler

        }

        private void doPost() {

        }

    }
}
