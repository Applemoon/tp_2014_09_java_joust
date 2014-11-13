package interfaces;

/**
 * Created by alexey on 11.10.2014.
 */
public interface AccountService {
    boolean validLoginAndPass(String login, String password);

    boolean signIn(String sessionId, String login, String password);

    boolean signUp(UserProfile user);

    void logOut(String sessionId);

    boolean iSignedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    int getAmountOfRegisteredUsers();

    int getAmountOfUsersOnline();
}
