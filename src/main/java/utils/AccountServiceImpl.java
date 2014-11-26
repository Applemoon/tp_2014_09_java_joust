package utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;

import db.UserProfileImpl;
import interfaces.AccountService;
import interfaces.UserProfile;


public class AccountServiceImpl implements AccountService {
    private final Map<String, UserProfile> users = new HashMap<>();
    private final BiMap<String, String> userSessions = HashBiMap.create();


    public AccountServiceImpl() {
        users.put("admin", new UserProfileImpl("admin", "admin"));
        users.put("test", new UserProfileImpl("test", "test"));
    }

    @Override
    public boolean validLoginAndPass(String login, String password) {
        // TODO прикрутить БД
        return (users.containsKey(login) && users.get(login).getPass().equals(password));
    }

    @Override
    public boolean signIn(String sessionId, String login) {
        if (!iSignedIn(sessionId)) {
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
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
        userSessions.inverse().remove(sessionId);
    }

    @Override
    public boolean iSignedIn(String sessionId) {
        return userSessions.inverse().containsKey(sessionId);
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (iSignedIn(sessionId)) {
            final String login = userSessions.inverse().get(sessionId);
            return users.get(login);
        }
        return null;
    }

    @Override
    public int getAmountOfRegisteredUsers() {
        return users.size();
    }

    @Override
    public int getAmountOfUsersOnline() {
        return userSessions.size();
    }
}
