package tests;

import interfaces.UserProfile;
import interfaces.AccountService;

import db.UserProfileImpl;
import utils.AccountServiceImpl;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest extends TestCase {

    private AccountService accountService;
    private static int counter = 0;

    private UserProfile createUser() {
        return new UserProfileImpl("1", "1");
    }

    private String getSessionId() {
        return "sessionId";
    }

    @Before
    public void setUp() throws Exception {
        System.out.printf("Test %d\n", ++counter);
        accountService = new AccountServiceImpl();
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

        final boolean expectedSuccessSignInResult = accountService.signIn("sessionId", user.getLogin());
        assertTrue(expectedSuccessSignInResult);


        int amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        assertEquals(before + 1, amountOfUsersOnline);


        boolean expectedFailSignInResult = accountService.signIn("sessionId", user.getLogin());
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
        accountService.signIn(sessionId, user.getLogin());

        final boolean expectedSuccessIsLoggedInResult = accountService.iSignedIn(sessionId);
        assertTrue(expectedSuccessIsLoggedInResult);

        accountService.logOut(sessionId);
        final boolean expectedFailIsLoggedInResult = accountService.iSignedIn(sessionId);
        assertFalse(expectedFailIsLoggedInResult);
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        assertFalse(accountService.iSignedIn(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        assertTrue(accountService.iSignedIn(sessionId));

        accountService.logOut(sessionId);
        assertFalse(accountService.iSignedIn(sessionId));
    }

    @Test
    public void testGetUserProfile() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        assertNull(accountService.getUserProfile(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        UserProfile signInUser = accountService.getUserProfile(sessionId);
        assertNotNull(signInUser);

        assertEquals(user, signInUser);
        assertTrue(user.equals(signInUser));
    }

    @Test
    public void testGetAmountOfRegisteredUsers() throws Exception {
        assertEquals(2, accountService.getAmountOfRegisteredUsers());

        UserProfile user = createUser();
        accountService.signUp(user);

        assertEquals(3, accountService.getAmountOfRegisteredUsers());
    }

    @Test
    public void testGetAmountOfUsersOnline() throws Exception {
        assertEquals(0, accountService.getAmountOfUsersOnline());

        UserProfile user = createUser();
        accountService.signUp(user);
        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin());

        assertEquals(1, accountService.getAmountOfUsersOnline());

        accountService.logOut(sessionId);

        assertEquals(0, accountService.getAmountOfUsersOnline());
    }
}
