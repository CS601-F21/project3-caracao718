package server;

public interface Handler {
    /**
     * Called by the handlers. Each web application will implement a different set of handlers.
     */
    public void handle();


}
