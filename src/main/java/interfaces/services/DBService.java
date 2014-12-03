package interfaces.services;

import db.UserProfile;

public interface DBService {
    boolean validateUser(String username, String password);

    void createUser(String username, String password);

    boolean isUserExists(String username);

    UserProfile getUserProfile(String username);

    int getAmountOfRegisteredUsers();

    void deleteUser(String username);
}
