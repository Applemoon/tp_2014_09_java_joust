package servlets;

import interfaces.services.AccountService;
import interfaces.services.DBService;
import org.junit.Before;
import org.junit.Test;
import services.AccountServiceImpl;
import services.DBServiceImpl;

public class AdminPageServletImplTest {
    private AdminPageServletImpl servlet;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        DBService dbService = new DBServiceImpl();
        accountService = new AccountServiceImpl(dbService);
        servlet = new AdminPageServletImpl(accountService);
    }

    @Test
    public void testDoGet() throws Exception {
        // TODO
    }
}