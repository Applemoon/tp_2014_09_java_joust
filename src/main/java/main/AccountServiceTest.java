package main;

import junit.framework.TestCase;
import org.eclipse.jetty.server.Authentication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest extends TestCase {

    private AccountService accountService;
    private static int counter = 0;

    private UserProfile createUser() {
        return new UserProfile("1", "1", "1");
    }

    private String getSessionId() {
        return "sessionId";
    }

    @Before
    public void setUp() throws Exception {
        System.out.printf("Test %d\n", ++counter);
        accountService = new AccountService();
    }

    @After
    public void tearDown() throws Exception {
        accountService = null;
        System.out.printf("End Test %d\n", counter);
    }

    @Test
    public void testSignIn() throws Exception {
        final int before = accountService.getAmountOfUsersOnline();
        UserProfile user = createUser();
        final boolean expectedSuccessSignUpResultResult = accountService.signUp(user);
        assertTrue(expectedSuccessSignUpResultResult);

        final boolean expectedSuccessSignInResult = accountService.signIn("sessionId", user.getLogin(), user.getPass());
        assertTrue(expectedSuccessSignInResult);


        int amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        assertEquals(before + 1, amountOfUsersOnline);


        boolean expectedFailSignInResult = accountService.signIn("sessionId", user.getLogin(), user.getPass());
        assertFalse(expectedFailSignInResult);

        amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        assertEquals(before + 1, amountOfUsersOnline);
    }

    @Test
    public void testSignUp() throws Exception {
        assertEquals(2, accountService.getAmountOfRegisteredUsers()); //"admin" and "test" users already created

        UserProfile user = createUser();
        boolean expectedSuccessSignUpResult = accountService.signUp(user);
        assertTrue(expectedSuccessSignUpResult);

        boolean expectedFailedSignUpResult = accountService.signUp(user);
        assertFalse(expectedFailedSignUpResult);
    }

    @Test
    public void testLogOut() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin(), user.getPass());

        final boolean expectedSuccessIsLoggedInResult = accountService.isLoggedIn(sessionId);
        assertTrue(expectedSuccessIsLoggedInResult);

        accountService.logOut(sessionId);
        final boolean expectedFailIsLoggedInResult = accountService.isLoggedIn(sessionId);
        assertFalse(expectedFailIsLoggedInResult);
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        assertFalse(accountService.isLoggedIn(sessionId));

        accountService.signIn(sessionId, user.getLogin(), user.getLogin());
        assertTrue(accountService.isLoggedIn(sessionId));

        accountService.logOut(sessionId);
        assertFalse(accountService.isLoggedIn(sessionId));
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