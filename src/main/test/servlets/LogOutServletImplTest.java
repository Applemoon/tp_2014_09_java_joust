package servlets;

import services.DBServiceImpl;
import interfaces.services.AccountService;
import interfaces.services.DBService;
import servlets.LogOutServletImpl;
import services.AccountServiceImpl;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import static org.mockito.Mockito.*;

public class LogOutServletImplTest {
    private LogOutServletImpl servlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private PrintWriter mockWriter;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        DBService dbService = new DBServiceImpl();
        accountService = new AccountServiceImpl(dbService);
        servlet = new LogOutServletImpl(accountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        HttpSession mockSession = mock(HttpSession.class);
        mockWriter = mock(PrintWriter.class);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testDoPost() throws Exception {
        final String sessionId = "sessionId";
        final String login = "test";
        accountService.signIn(sessionId, login);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, never()).println(anyString());

        accountService.logOut(sessionId);
    }
}