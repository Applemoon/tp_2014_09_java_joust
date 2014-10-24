package tests;

import frontend.SignInServletImpl;
import interfaces.SignInServlet;
import org.junit.Before;
import org.junit.Test;
import utils.AccountServiceImpl;

import static org.junit.Assert.*;

public class SignInServletTest {
    private SignInServlet signInServlet;

    @Before
    public void setUp() throws Exception {
        signInServlet = new SignInServletImpl(new AccountServiceImpl());
    }

    @Test
    public void testDoGet() throws Exception {
        // TODO
    }

    @Test
    public void testDoPost() throws Exception {
        // TODO
    }
}