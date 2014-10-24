package tests;

import frontend.FrontendServlet;
import interfaces.AccountService;
import org.junit.Before;
import org.junit.Test;
import utils.AccountServiceImpl;

import static org.junit.Assert.*;

public class FrontendServletTest {
    private FrontendServlet frontendServlet;

    @Before
    public void setUp() throws Exception {
        frontendServlet = new FrontendServlet(new AccountServiceImpl());
    }

    @Test
    public void testDoGet() throws Exception {
        // TODO
    }
}