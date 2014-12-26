package db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserProfileTest {
    private UserProfile user1;
    private UserProfile user2;

    private String getPass() {
        return "testPass48@_GGG";
    }

    private String getLogin() {
        return "testLogin";
    }

    private int getGamesPlayed() {
        return 42;
    }

    private int getGamesWon() {
        return 3;
    }

    @Before
    public void setUp() throws Exception {
        user1 = new UserProfile(getLogin(), getPass());
        user2 = new UserProfile(getLogin(), getPass(), getGamesPlayed(), getGamesWon());
    }

    @Test
    public void testGetLogin() throws Exception {
        assertEquals(getLogin(), user1.getLogin());
        assertEquals(getLogin(), user2.getLogin());
    }

    @Test
    public void testGetPass() throws Exception {
        assertEquals(getPass(), user1.getPass());
        assertEquals(getPass(), user2.getPass());
    }

    @Test
    public void testGetGamesPlayed() throws Exception {
        assertEquals(0, user1.getGamesPlayed());
        assertEquals(getGamesPlayed(), user2.getGamesPlayed());
    }

    @Test
    public void testGetGamesWon() throws Exception {
        assertEquals(0, user1.getGamesWon());
        assertEquals(getGamesWon(), user2.getGamesWon());
    }
}