package utils;

import interfaces.AccountService;
import interfaces.UserProfile;

import db.UserProfileImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 13.09.14.
 */
public class AccountServiceImpl implements AccountService {
    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, String> sessions = new HashMap<>(); // (id, login)
    private Map<String, String> userSessions = new HashMap<>(); // (login, id)


    public AccountServiceImpl() {
        users.put("admin", new UserProfileImpl("admin", "admin", "admin"));
        users.put("test", new UserProfileImpl("test", "test", "test"));
    }

    @Override
    public boolean validLoginAndPass(String login, String password) {
        return (users.containsKey(login) && users.get(login).getPass().equals(password));
    }

    @Override
    public boolean signIn(String sessionId, String login, String password) {
        if (!iSignedIn(sessionId)) {
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
            sessions.put(sessionId, login);
            userSessions.put(login, sessionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean signUp(UserProfile user) {
        if (users.containsKey(user.getLogin()))
            return false;
        users.put(user.getLogin(), user); // TODO прикрутить БД
        return true;
    }

    @Override
    public void logOut(String sessionId) {
        if (iSignedIn(sessionId)) {
            userSessions.remove(sessions.get(sessionId));
            sessions.remove(sessionId);
        }
    }

    @Override
    public boolean iSignedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (iSignedIn(sessionId))
            return users.get(sessions.get(sessionId));
        return null;
    }

    @Override
    public int getAmountOfRegisteredUsers() {
        return users.size();
    }

    @Override
    public int getAmountOfUsersOnline() {
        return sessions.size();
    }
}
