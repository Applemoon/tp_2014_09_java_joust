package servlets;

import services.DBServiceImpl;
import interfaces.services.AccountService;
import interfaces.services.DBService;
import servlets.SignInServletImpl;
import services.AccountServiceImpl;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SignInServletImplTest {
    private SignInServletImpl servlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private PrintWriter mockWriter;

    @Before
    public void setUp() throws Exception {
        DBService dbService = new DBServiceImpl();
        AccountService accountService = new AccountServiceImpl(dbService);
        servlet = new SignInServletImpl(accountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        HttpSession mockSession = mock(HttpSession.class);
        mockWriter = mock(PrintWriter.class);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testDoPostWithRightPass() throws Exception {
        final String login = "test";
        final String password = "test";
        when(mockRequest.getParameter("login")).thenReturn(login);
        when(mockRequest.getParameter("password")).thenReturn(password);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithWrongPass() throws Exception {
        final String login = "test";
        final String password = "wrongtest";
        when(mockRequest.getParameter("login")).thenReturn(login);
        when(mockRequest.getParameter("password")).thenReturn(password);

        servlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(mockWriter, atLeastOnce()).println(anyString());
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