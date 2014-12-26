package db;

public class UserProfile {
    private final String login;
    private final String pass;
    private final int gamesPlayed;
    private final int gamesWon;

    public UserProfile(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    public UserProfile(String login, String pass, int gamesPlayed, int gamesWon) {
        this.login = login;
        this.pass = pass;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }
}
