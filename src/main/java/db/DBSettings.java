package db;

public class DBSettings {
    private final String type;
    private final String host;
    private final int port;
    private final String name;
    private final String login;
    private final String password;

    public DBSettings() {
        this.type = "";
        this.host = "";
        this.port = 0;
        this.name = "";
        this.login = "";
        this.password = "";
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
