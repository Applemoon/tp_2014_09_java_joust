package tests;

import db.DBServiceImpl;
import interfaces.AccountService;

import db.UserProfile;
import interfaces.DBService;
import org.junit.Assert;
import utils.AccountServiceImpl;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest {

    private AccountService accountService;
    private static int counter = 0;

    private UserProfile createUser() {
        return new UserProfile("1", "1");
    }

    private String getSessionId() {
        return "sessionId";
    }

    @Before
    public void setUp() throws Exception {
        System.out.printf("Test %d\n", ++counter);
        DBService dbService = new DBServiceImpl();
        accountService = new AccountServiceImpl(dbService);
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
        Assert.assertTrue(expectedSuccessSignUpResultResult);

        final boolean expectedSuccessSignInResult = accountService.signIn("sessionId", user.getLogin());
        Assert.assertTrue(expectedSuccessSignInResult);


        int amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        Assert.assertEquals(before + 1, amountOfUsersOnline);


        boolean expectedFailSignInResult = accountService.signIn("sessionId", user.getLogin());
        Assert.assertFalse(expectedFailSignInResult);

        amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        Assert.assertEquals(before + 1, amountOfUsersOnline);
    }

    @Test
    public void testSignUp() throws Exception {
        Assert.assertEquals(2, accountService.getAmountOfRegisteredUsers()); //"admin" and "test" users already created

        UserProfile user = createUser();
        boolean expectedSuccessSignUpResult = accountService.signUp(user);
        Assert.assertTrue(expectedSuccessSignUpResult);

        boolean expectedFailedSignUpResult = accountService.signUp(user);
        Assert.assertFalse(expectedFailedSignUpResult);
    }

    @Test
    public void testLogOut() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin());

        final boolean expectedSuccessIsLoggedInResult = accountService.iSignedIn(sessionId);
        Assert.assertTrue(expectedSuccessIsLoggedInResult);

        accountService.logOut(sessionId);
        final boolean expectedFailIsLoggedInResult = accountService.iSignedIn(sessionId);
        Assert.assertFalse(expectedFailIsLoggedInResult);
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        Assert.assertFalse(accountService.iSignedIn(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        Assert.assertTrue(accountService.iSignedIn(sessionId));

        accountService.logOut(sessionId);
        Assert.assertFalse(accountService.iSignedIn(sessionId));
    }

    @Test
    public void testGetUserProfile() throws Exception {
        UserProfile user = createUser();
        accountService.signUp(user);

        String sessionId = getSessionId();
        Assert.assertNull(accountService.getUserProfile(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        UserProfile signInUser = accountService.getUserProfile(sessionId);
        Assert.assertNotNull(signInUser);

        Assert.assertEquals(user, signInUser);
        Assert.assertTrue(user.equals(signInUser));
    }

    @Test
    public void testGetAmountOfRegisteredUsers() throws Exception {
        Assert.assertEquals(2, accountService.getAmountOfRegisteredUsers());

        UserProfile user = createUser();
        accountService.signUp(user);

        Assert.assertEquals(3, accountService.getAmountOfRegisteredUsers());
    }

    @Test
    public void testGetAmountOfUsersOnline() throws Exception {
        Assert.assertEquals(0, accountService.getAmountOfUsersOnline());

        UserProfile user = createUser();
        accountService.signUp(user);
        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin());

        Assert.assertEquals(1, accountService.getAmountOfUsersOnline());

        accountService.logOut(sessionId);

        Assert.assertEquals(0, accountService.getAmountOfUsersOnline());
    }
}
