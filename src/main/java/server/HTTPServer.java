package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
        if (path.equals("/reviewsearch")) {

        } else if (path.equals("/find")) {

        } else {
            // return status code
        }
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
                // read in the first line
                String requestLine = instream.readLine();
                LOGGER.info("Request: " + requestLine);
                String[] requestLineParts = requestLine.split("\\s");
                if (requestLineParts.length != 3) {
                    // bad request 400
                }
                String method = requestLineParts[0];
                String path = requestLineParts[1];
                String version = requestLineParts[2];

                validMethod(method);
                LOGGER.debug("Http Method: " + method);
                LOGGER.debug("Path: " + path);
                LOGGER.debug("Http Version: " + version);

                // get the content length
                int contentLength = getContentLength(instream);

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

        private int getContentLength(BufferedReader instream) {
            int contentLength = 0;
            List<String> headers = new ArrayList<>();
            String header;
            try {
                while (!(header = instream.readLine()).isEmpty()) {
                    headers.add(header);

                    // need to check if content-length header is correctly formatted
                    if (header.startsWith("CONTENT_LENGTH")) {
                        String[] contentLengthParts = header.split("\\s");
                        contentLength = Integer.parseInt(contentLengthParts[1]);
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if (contentLength == 0) {
                // bad request
                // finish job send response
                return -1;
            }
            return contentLength;
        }

        private void validMethod(String method) {
            if (!method.equals("GET") && !method.equals("POST")) {
                // bad request
                // stop thread
            } else {
                LOGGER.info("method is valid");
            }
        }
    }
}
