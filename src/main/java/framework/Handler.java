package framework;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * Each web application will implement a different set of handlers.
 */
public interface Handler {

    /**
     * Set the Handler
     * @param handler
     */
    public void handle(Handler handler);

    /**
     * Set the paths for each handler
     * @param paths
     */
    public void setPath(HashSet<String> paths);

    /**
     * Set the method for each request
     * @param method
     */
    public void setMethod(String method);

    /**
     * Set the reader for each handler
     * @param reader
     */
    public void setReader(BufferedReader reader);

    /**
     * Set the writer for each handler
     * @param writer
     */
    public void setWriter(PrintWriter writer);

    /**
     * Set the content length for POST method. This is optional
     * @param contentLength
     */
    public void setContentLength(int contentLength);


}
