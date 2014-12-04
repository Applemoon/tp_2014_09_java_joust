package servlets;

import interfaces.services.AccountService;
import interfaces.services.DBService;
import interfaces.services.WebSocketService;
import org.junit.Before;
import org.junit.Test;
import services.AccountServiceImpl;
import services.DBServiceImpl;
import services.WebSocketServiceImpl;

import static org.junit.Assert.*;

public class WebSocketGameServletTest {
    private WebSocketGameServlet servlet;
    private DBService dbService;
    private AccountService accountService;
    private WebSocketService webSocketService;

    @Before
    public void setUp() throws Exception {
        webSocketService = new WebSocketServiceImpl();
        dbService =  new DBServiceImpl();
        accountService = new AccountServiceImpl(dbService);
        servlet = new WebSocketGameServlet(webSocketService, accountService);
    }

    // TODO ?
}