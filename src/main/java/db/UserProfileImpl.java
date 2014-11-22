package db;

import interfaces.UserProfile;

/**
 * Created by alexey on 13.09.14.
 * TODO удалить класс, прикрутить БД
 */
public class UserProfileImpl implements UserProfile {
    private final String login;
    private final String pass;

    public UserProfileImpl(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPass() {
        return pass;
    }
}
