package services;

import db.UserProfile;
import interfaces.services.AccountService;
import interfaces.services.DBService;
import messageSystem.*;
import messageSystem.messages.Message;
import messageSystem.messages.MessageSignUp;
import utils.ProcessState;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class AccountServiceImpl implements AccountService, Runnable {
    private final BiMap<String, String> userSessions = HashBiMap.create();
    private final Map<String, ProcessState> waitingSignUpUsers = new HashMap<>();
    private final DBService dbService;
    private final Address address = new Address();
    private final MessageSystem messageSystem;


    public AccountServiceImpl(DBService dbService, MessageSystem messageSystem) {
        this.dbService = dbService;
        dbService.createUser("admin", "admin");
        dbService.createUser("test", "test");

        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerAccountService(this);
    }

    @Override
    public void setSignUpState(String login, ProcessState signUpState) {
        waitingSignUpUsers.put(login, signUpState);
    }

    @Override
    public ProcessState getSignUpState(String login) {
        final ProcessState state = waitingSignUpUsers.get(login);
        if (state == null) {
            return ProcessState.Doing;
        }
        return state;
    }

    @Override
    public boolean validLoginAndPass(String login, String password) {
        return dbService.validateUser(login, password);
    }

    @Override
    public boolean signIn(String sessionId, String login) {
        if (!isSignedIn(sessionId)) {
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
            userSessions.put(login, sessionId);
            return true;
        }
        return false;
    }

    @Override
    public void signUp(UserProfile user) {
        setSignUpState(user.getLogin(), ProcessState.Doing);
        final Message messageSignUp = new MessageSignUp(
                getAddress(),
                messageSystem.getAddressService().getDBServiceAddress(),
                user);
        messageSystem.sendMessage(messageSignUp);
    }

    @Override
    public void logOut(String sessionId) {
        userSessions.inverse().remove(sessionId);
    }

    @Override
    public boolean isSignedIn(String sessionId) {
        return userSessions.inverse().containsKey(sessionId);
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (isSignedIn(sessionId)) {
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

    @Override
    public void deleteUser(UserProfile user) {
        final String sessionId = userSessions.get(user.getLogin());
        logOut(sessionId);
        dbService.deleteUser(user.getLogin());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
