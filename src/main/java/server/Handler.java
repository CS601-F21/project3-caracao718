package server;

public interface Handler {
    /**
     * Called by the handlers. Each web application will implement a different set of handlers.
     */
    public void handler();

    /**
     * Maps a URI path to a specific Handler instance.
     */
    public void addMapping();
}
