package interfaces;

/**
 * Created by alexey on 11.10.2014.
 */
public interface AccountService {
    boolean signIn(String sessionId, String login, String password);

    boolean signUp(UserProfile user);

    void logOut(String sessionId);

    boolean isLoggedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    int getAmountOfRegisteredUsers();

    int getAmountOfUsersOnline();
}
