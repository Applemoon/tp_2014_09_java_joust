package main;

import java.io.Serializable;

public class ServerSettings implements Serializable {
    private final int port;

    public ServerSettings() {
        this.port = 0;
    }

    public int getPort() {
        return port;
    }
}
