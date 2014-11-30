package interfaces;

import db.UserProfile;

public interface DBService {
    boolean validateUser(String username, String password);

    void createUser(String username, String password);

    boolean isUserExists(String username);

    UserProfile getUserProfile(String username);

//    int getUserPlayedGames(String username);

//    int getUserWonGames(String username);

    int getAmountOfRegisteredUsers();
}
