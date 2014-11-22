package base;

import java.io.Serializable;

public class Port implements Serializable {
    private final int port;

    public Port() {
        this.port = 0;
    }

    public int getPort() {
        return port;
    }
}
