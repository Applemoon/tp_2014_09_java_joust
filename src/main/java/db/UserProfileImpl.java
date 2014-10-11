package db;

import interfaces.UserProfile;

/**
 * Created by alexey on 13.09.14.
 */
public class UserProfileImpl implements UserProfile {
    private final String login;
    private final String pass;
    private final String email;

    public UserProfileImpl(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
