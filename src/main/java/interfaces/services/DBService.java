package interfaces.services;

import db.UserProfile;
import interfaces.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

public interface DBService extends Abonent, Runnable {
    boolean validateUser(String username, String password);

    void createUser(String username, String password);

    boolean isUserExists(String username);

    UserProfile getUserProfile(String username);

    int getAmountOfRegisteredUsers();

    void deleteUser(String username);

    MessageSystem getMessageSystem();

    Address getAddress();
}
