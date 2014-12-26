package services;

import db.UserProfile;
import messageSystem.MessageSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBServiceImplTest {
    private DBServiceImpl dbService;

    @Before
    public void setUp() throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        dbService = new DBServiceImpl(messageSystem);
    }

    @Test
    public void testValidateUser() throws Exception {
        assertTrue(dbService.validateUser("test", "test"));
        assertTrue(dbService.validateUser("admin", "admin"));
        assertFalse(dbService.validateUser("testwrong", "test"));
        assertFalse(dbService.validateUser("test", "testwrong"));
        assertFalse(dbService.validateUser("testwrong", "testwrong"));
    }

    private String getUsername() {
        return "username";
    }

    @Test
    public void testCreateUser() throws Exception {
        createAndDeleteUser();
    }

    @Test
    public void testIsUserExists() throws Exception {
        assertTrue(dbService.isUserExists("test"));
        assertTrue(dbService.isUserExists("admin"));
        assertFalse(dbService.isUserExists("testwrong"));
    }

    @Test
    public void testGetUserProfile() throws Exception {
        UserProfile user = dbService.getUserProfile("test");

        assertEquals("test", user.getLogin());
        assertEquals("test", user.getPass());
        assertEquals(0, user.getGamesPlayed());
        assertEquals(0, user.getGamesWon());
    }

    void createAndDeleteUser() {
        int usersCount = dbService.getAmountOfRegisteredUsers();

        dbService.createUser(getUsername(), "test2");
        usersCount++;
        assertEquals(usersCount, dbService.getAmountOfRegisteredUsers());

        dbService.deleteUser(getUsername());
        usersCount--;
        assertEquals(usersCount, dbService.getAmountOfRegisteredUsers());
    }

    @Test
    public void testGetAmountOfRegisteredUsers() throws Exception {
        createAndDeleteUser();
    }

    @Test
    public void testDeleteUser() throws Exception {
        createAndDeleteUser();
    }
}