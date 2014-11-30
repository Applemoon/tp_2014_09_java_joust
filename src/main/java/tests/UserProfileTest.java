package tests;

import db.UserProfile;

import junit.framework.TestCase;

public class UserProfileTest extends TestCase {

    private UserProfile user;

    public void setUp() throws Exception {
        super.setUp();
        user = new UserProfile(getLogin(), getPass());
    }

    public void testGetLogin() throws Exception {
        assertEquals(getLogin(), user.getLogin());
    }

    public void testGetPass() throws Exception {
        assertEquals(getPass(), user.getPass());
    }

    private String getPass() {
        return "testPass48@_GGG";
    }

    private String getLogin() {
        return "testLogin";
    }
}