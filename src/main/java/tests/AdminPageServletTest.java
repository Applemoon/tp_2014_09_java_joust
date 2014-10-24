package tests;

import admin.AdminPageServletImpl;
import interfaces.AdminPageServlet;
import org.junit.Before;
import org.junit.Test;
import utils.AccountServiceImpl;

public class AdminPageServletTest {
    private AdminPageServlet adminPageServlet;

    @Before
    public void setUp() throws Exception {
        adminPageServlet = new AdminPageServletImpl(new AccountServiceImpl());
    }

    @Test
    public void testDoGet() throws Exception {
        // TODO
    }
}