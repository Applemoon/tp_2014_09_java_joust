package main;

import junit.framework.TestCase;
import org.eclipse.jetty.server.Authentication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest extends TestCase {

    private AccountService accountService = new AccountService();
    private static int counter = 0;
    private UserProfile user = new UserProfile("1", "1", "1");

    @Before
    public void setUp() throws Exception {
        System.out.printf("Test %d\n", ++counter);
        accountService.signUp(user);
    }

    @After
    public void tearDown() throws Exception {
        System.out.printf("End Test %d\n", counter);
    }

    @Test
    public void testSignIn() throws Exception {
        int before = accountService.getAmountOfUsersOnline();
        assertTrue(accountService.signIn("sessionId", user.getLogin(), user.getPass()));
        assertEquals(before + 1, accountService.getAmountOfUsersOnline());
        assertFalse(accountService.signIn("sessionId", user.getLogin(), user.getPass()));
        assertEquals(before + 1, accountService.getAmountOfUsersOnline());
    }

    @Test
    public void testSignUp() throws Exception {
        int before = accountService.getAmountOfRegisteredUsers();
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
        assertEquals(3, accountService.getAmountOfRegisteredUsers());
    }

    @Test
    public void testGetAmountOfUsersOnline() throws Exception {
        assertEquals(0, accountService.getAmountOfUsersOnline());
    }
}