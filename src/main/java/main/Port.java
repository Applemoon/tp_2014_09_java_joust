package main;

import java.io.Serializable;

/**
 * Created by applemoon on 18.10.14.
 */
public class Port implements Serializable {
    private int port;

    public Port() {
        this.port = 0;
    }

    public int getPort() {
        return port;
    }
}
