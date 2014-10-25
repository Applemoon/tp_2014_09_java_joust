package db;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserProfileImplTest {

    private UserProfileImpl user;

    @Before
    public void setUp() throws Exception {
        user = new UserProfileImpl(getLogin(), getPass(), getEmail());
    }

    @Test
    public void testGetLogin() throws Exception {
        assertEquals(getLogin(), user.getLogin());
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals(getEmail(), user.getEmail());
    }

    @Test
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