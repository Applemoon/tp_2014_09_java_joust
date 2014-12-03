package services;

import services.DBServiceImpl;
import db.UserProfile;
import interfaces.services.DBService;

import org.junit.Before;
import org.junit.Test;
import services.AccountServiceImpl;

import static org.junit.Assert.*;

public class AccountServiceImplTest {
    private AccountServiceImpl accountService;
    private int usersCount;

    private UserProfile createUser() {
        return new UserProfile("1", "1");
    }

    private UserProfile signUpUser() {
        UserProfile user = createUser();
        accountService.signUp(user);
        usersCount++;
        return user;
    }

    private void deleteUser(UserProfile user) {
        accountService.deleteUser(user);
        usersCount--;
    }

    private String getSessionId() {
        return "sessionId";
    }

    @Before
    public void setUp() {
        DBService dbService = new DBServiceImpl();
        accountService = new AccountServiceImpl(dbService);
        usersCount = 2;
    }

    @Test
    public void testValidLoginAndPass() {
        UserProfile user = signUpUser();

        final boolean expectedResult = accountService.validLoginAndPass(user.getLogin(), user.getPass());
        assertTrue(expectedResult);

        accountService.deleteUser(user);
        usersCount--;
    }

    @Test
    public void testSignIn() {
        final int before = accountService.getAmountOfUsersOnline();
        UserProfile user = createUser();
        final boolean expectedSuccessSignUpResultResult = accountService.signUp(user);
        usersCount++;
        assertTrue(expectedSuccessSignUpResultResult);

        final boolean expectedSuccessSignInResult = accountService.signIn("sessionId", user.getLogin());
        assertTrue(expectedSuccessSignInResult);

        int amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        assertEquals(before + 1, amountOfUsersOnline);

        boolean expectedFailSignInResult = accountService.signIn("sessionId", user.getLogin());
        assertFalse(expectedFailSignInResult);

        amountOfUsersOnline = accountService.getAmountOfUsersOnline();
        assertEquals(before + 1, amountOfUsersOnline);

        deleteUser(user);
    }

    @Test
    public void testSignUp() {
        assertEquals(usersCount, accountService.getAmountOfRegisteredUsers()); //"admin" and "test" users already created

        UserProfile user = createUser();
        boolean expectedSuccessSignUpResult = accountService.signUp(user);
        usersCount++;
        assertTrue(expectedSuccessSignUpResult);

        boolean expectedFailedSignUpResult = accountService.signUp(user);
        assertFalse(expectedFailedSignUpResult);

        deleteUser(user);
    }

    @Test
    public void testLogOut() {
        UserProfile user = signUpUser();

        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin());

        final boolean expectedSuccessIsLoggedInResult = accountService.isSignedIn(sessionId);
        assertTrue(expectedSuccessIsLoggedInResult);

        accountService.logOut(sessionId);
        final boolean expectedFailIsLoggedInResult = accountService.isSignedIn(sessionId);
        assertFalse(expectedFailIsLoggedInResult);

        deleteUser(user);
    }

    @Test
    public void testISignedIn() {
        UserProfile user = signUpUser();

        String sessionId = getSessionId();
        assertFalse(accountService.isSignedIn(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        assertTrue(accountService.isSignedIn(sessionId));

        accountService.logOut(sessionId);
        assertFalse(accountService.isSignedIn(sessionId));

        deleteUser(user);
    }

    @Test
    public void testGetUserProfile() {
        UserProfile user = signUpUser();

        String sessionId = getSessionId();
        assertNull(accountService.getUserProfile(sessionId));

        accountService.signIn(sessionId, user.getLogin());
        UserProfile signInUser = accountService.getUserProfile(sessionId);
        assertNotNull(signInUser);

        assertEquals(user.getLogin(), signInUser.getLogin());
        assertEquals(user.getPass(), signInUser.getPass());

        deleteUser(user);
    }

    @Test
    public void testGetAmountOfRegisteredUsers() {
        assertEquals(usersCount, accountService.getAmountOfRegisteredUsers());

        UserProfile user = signUpUser();

        assertEquals(usersCount, accountService.getAmountOfRegisteredUsers());

        deleteUser(user);
    }

    @Test
    public void testGetAmountOfUsersOnline() {
        assertEquals(0, accountService.getAmountOfUsersOnline());

        UserProfile user = signUpUser();

        String sessionId = getSessionId();
        accountService.signIn(sessionId, user.getLogin());

        assertEquals(1, accountService.getAmountOfUsersOnline());

        accountService.logOut(sessionId);

        assertEquals(0, accountService.getAmountOfUsersOnline());

        deleteUser(user);
    }

    @Test
    public void testDeleteUser() {
        assertEquals(usersCount, accountService.getAmountOfRegisteredUsers());

        UserProfile user = createUser();
        usersCount++;
        accountService.deleteUser(user);
        usersCount--;

        assertEquals(usersCount, accountService.getAmountOfRegisteredUsers());
    }
}