package utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import db.UserProfile;
import interfaces.AccountService;
import interfaces.DBService;


public class AccountServiceImpl implements AccountService {
    private final BiMap<String, String> userSessions = HashBiMap.create();
    private DBService dbService;


    public AccountServiceImpl(DBService dbService) {
        this.dbService = dbService;
        dbService.createUser("admin", "admin");
        dbService.createUser("test", "test");
    }

    @Override
    public boolean validLoginAndPass(String login, String password) {
        return dbService.validateUser(login, password);
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
        if (dbService.isUserExists(user.getLogin()))
            return false;
        dbService.createUser(user.getLogin(), user.getPass());
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
            return dbService.getUserProfile(login);
        }
        return null;
    }

    @Override
    public int getAmountOfRegisteredUsers() {
        return dbService.getAmountOfRegisteredUsers();
    }

    @Override
    public int getAmountOfUsersOnline() {
        return userSessions.size();
    }
}
