package tests;

import frontend.SignUpServletImpl;
import interfaces.SignUpServlet;
import org.junit.Before;
import org.junit.Test;
import utils.AccountServiceImpl;

import static org.junit.Assert.*;

public class SignUpServletTest {
    SignUpServlet signUpServlet;

    @Before
    public void setUp() throws Exception {
        signUpServlet = new SignUpServletImpl(new AccountServiceImpl());
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