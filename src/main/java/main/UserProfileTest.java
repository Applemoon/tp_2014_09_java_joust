package main;

import junit.framework.TestCase;

public class UserProfileTest extends TestCase {

    private UserProfile user;

    public void setUp() throws Exception {
        super.setUp();
        user = new UserProfile(getLogin(), getPass(), getEmail());
    }

    public void testGetLogin() throws Exception {
        assertEquals(getLogin(), user.getLogin());
    }

    public void testGetEmail() throws Exception {
        assertEquals(getEmail(), user.getEmail());
    }

    public void testGetPass() throws Exception {
        assertEquals(getPass(), user.getPass());
    }

    private String getEmail() {
        return "test_mail@test_mail.org";
    }

    private String getPass() {
        return "testPass48@_GGG";
    }

    private String getLogin() {
        return "testLogin";
    }
}