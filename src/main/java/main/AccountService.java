package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 13.09.14.
 */
public class AccountService {

    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, String> sessions = new HashMap<>();
    private Map<String, String> userSessions = new HashMap<>();

    public AccountService() {
        users.put("admin", new UserProfile("admin", "admin", "admin"));
        users.put("test", new UserProfile("test", "test", "test"));
    }

    public boolean signIn(String sessionId, String login, String password) {
        boolean result = false;

        if (!isLoggedIn(sessionId) && users.containsKey(login) && users.get(login).getPass().equals(password)) {
            result = true;
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
            sessions.put(sessionId, login);
            userSessions.put(login, sessionId);
        }
        return result;
    }

    public boolean signUp(UserProfile user) {
        if (users.containsKey(user.getLogin()))
            return false;
        users.put(user.getLogin(), user);
        return true;
    }

    public void logOut(String sessionId) {
        if (isLoggedIn(sessionId)) {
            userSessions.remove(sessions.get(sessionId));
            sessions.remove(sessionId);
        }
    }

    public boolean isLoggedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public UserProfile getUserProfile(String sessionId) {
        if (isLoggedIn(sessionId))
            return users.get(sessions.get(sessionId));
        return null;
    }

    public int getAmountOfRegisteredUsers() {
        return users.size();
    }

    public int getAmountOfUsersOnline() {
        return sessions.size();
    }
}
