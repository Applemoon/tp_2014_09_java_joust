package interfaces.services;

import db.UserProfile;
import interfaces.Abonent;
import messageSystem.Address;
import utils.ProcessState;

public interface AccountService extends Abonent {
    void setSignUpState(String login, ProcessState signUpState);

    ProcessState getSignUpState(String login);

    boolean validLoginAndPass(String login, String password);

    boolean signIn(String sessionId, String login);

    void signUp(UserProfile user);

    void logOut(String sessionId);

    boolean isSignedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    int getAmountOfRegisteredUsers();

    int getAmountOfUsersOnline();

    void deleteUser(UserProfile user);

    Address getAddress();
}
