package db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserProfileTest {
    private UserProfile user;

    private String getPass() {
        return "testPass48@_GGG";
    }

    private String getLogin() {
        return "testLogin";
    }

    @Before
    public void setUp() throws Exception {
        user = new UserProfile(getLogin(), getPass());
    }

    @Test
    public void testGetLogin() throws Exception {
        assertEquals(getLogin(), user.getLogin());
    }

    @Test
    public void testGetPass() throws Exception {
        assertEquals(getPass(), user.getPass());
    }
}