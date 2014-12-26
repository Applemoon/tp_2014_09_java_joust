package servlets;

import messageSystem.MessageSystem;
import services.DBServiceImpl;
import db.UserProfile;
import interfaces.services.AccountService;
import interfaces.services.DBService;
import services.AccountServiceImpl;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import static org.mockito.Mockito.*;

public class SignUpServletImplTest {
    private SignUpServletImpl servlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private PrintWriter mockWriter;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        MessageSystem messageSystem = new MessageSystem();
        DBService dbService = new DBServiceImpl(messageSystem);
        accountService = new AccountServiceImpl(dbService, messageSystem);
        servlet = new SignUpServletImpl(accountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        HttpSession mockSession = mock(HttpSession.class);
        mockWriter = mock(PrintWriter.class);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testDoPostWithRightData() throws Exception {
        final String login = "lgn";
        final String password = "pswrd";
        when(mockRequest.getParameter("login")).thenReturn(login);
        when(mockRequest.getParameter("password")).thenReturn(password);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());

        UserProfile userProfile = new UserProfile(login, password);
        accountService.deleteUser(userProfile);
    }

    @Test
    public void testDoPostWithEmptyLogin() throws Exception {
        final String login = "";
        final String password = "CoOl@PasS%";
        when(mockRequest.getParameter("login")).thenReturn(login);
        when(mockRequest.getParameter("password")).thenReturn(password);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithEmptyPass() throws Exception {
        final String login = "lgn";
        final String password = "";
        when(mockRequest.getParameter("login")).thenReturn(login);
        when(mockRequest.getParameter("password")).thenReturn(password);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }
}