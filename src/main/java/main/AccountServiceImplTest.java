package main;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AccountServiceImplTest extends TestCase {

    private AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
    private static int counter = 0;
    private UserProfile user = new UserProfile("1", "1", "1");

    @Before
    public void setUp() throws Exception {
        System.out.printf("Test %d\n", ++counter);
        accountServiceImpl.signUp(user);
    }

    @After
    public void tearDown() throws Exception {
        System.out.printf("End Test %d\n", counter);
    }

    @Test
    public void testSignIn() throws Exception {
        int before = accountServiceImpl.getAmountOfUsersOnline();
        assertTrue(accountServiceImpl.signIn("sessionId", user.getLogin(), user.getPass()));
        assertEquals(before + 1, accountServiceImpl.getAmountOfUsersOnline());
        assertFalse(accountServiceImpl.signIn("sessionId", user.getLogin(), user.getPass()));
        assertEquals(before + 1, accountServiceImpl.getAmountOfUsersOnline());
    }

    @Test
    public void testSignUp() throws Exception {
        int before = accountServiceImpl.getAmountOfRegisteredUsers();
    }

    @Test
    public void testLogOut() throws Exception {

    }

    @Test
    public void testIsLoggedIn() throws Exception {

    }

    @Test
    public void testGetUserProfile() throws Exception {

    }

    @Test
    public void testGetAmountOfRegisteredUsers() throws Exception {
        assertEquals(3, accountServiceImpl.getAmountOfRegisteredUsers());
    }

    @Test
    public void testGetAmountOfUsersOnline() throws Exception {
        assertEquals(0, accountServiceImpl.getAmountOfUsersOnline());
    }
}