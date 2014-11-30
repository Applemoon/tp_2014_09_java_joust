package interfaces;

import db.UserProfile;

public interface AccountService {
    boolean validLoginAndPass(String login, String password);

    boolean signIn(String sessionId, String login);

    boolean signUp(UserProfile user);

    void logOut(String sessionId);

    boolean iSignedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    int getAmountOfRegisteredUsers();

    int getAmountOfUsersOnline();
}
