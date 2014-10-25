package frontend;

import interfaces.AccountService;

import interfaces.UserProfile;
import interfaces.UserProfileServlet;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class SignInServletImplTest {

    private SignInServletImpl signInServlet;

    private String sessionId = "sessionId";

    private AccountService mockAccountService = mock(AccountService.class);

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private HttpSession mockSession;
    private PrintWriter mockWriter;

    @Before
    public void setUp() throws Exception {
        signInServlet = new SignInServletImpl(mockAccountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);

        mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        mockWriter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testDoGetWhenLoggedIn() throws Exception {
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.isLoggedIn(sessionId)).thenReturn(true);

        signInServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).sendRedirect(UserProfileServlet.userProfilePageURL);
    }

    @Test
    public void testDoGetWhenNotLoggedIn() throws Exception {
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.isLoggedIn(sessionId)).thenReturn(false);
        when(mockResponse.getWriter()).thenReturn(mockWriter);

        signInServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse, atLeastOnce()).getWriter();
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithEmptyLogin() throws Exception {
        when(mockRequest.getParameter("login")).thenReturn("");
        when(mockRequest.getParameter("password")).thenReturn("CoOl@PasS%");

        signInServlet.doPost(mockRequest, mockResponse);

        verify(mockRequest, never()).getSession();
        verify(mockResponse, never()).sendRedirect(anyString());

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithEmptyPass() throws Exception {
        when(mockRequest.getParameter("login")).thenReturn("applemoon");
        when(mockRequest.getParameter("password")).thenReturn("");

        signInServlet.doPost(mockRequest, mockResponse);

        verify(mockRequest, never()).getSession();
        verify(mockResponse, never()).sendRedirect(anyString());

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithWrongPass() throws Exception {
        when(mockRequest.getParameter("login")).thenReturn("applemoon");
        when(mockRequest.getParameter("password")).thenReturn("CoOl@PasS%");
        when(mockAccountService.signIn(anyString(), anyString(), anyString())).thenReturn(false);

        signInServlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, never()).sendRedirect(anyString());
        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPostWithCorrectSignIn() throws Exception {
        when(mockRequest.getParameter("login")).thenReturn("applemoon");
        when(mockRequest.getParameter("password")).thenReturn("CoOl@PasS%");
        when(mockAccountService.signIn(anyString(), anyString(), anyString())).thenReturn(true);

        signInServlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).sendRedirect(UserProfileServlet.userProfilePageURL);
        verify(mockResponse, never()).setStatus(anyInt());
        verify(mockResponse, never()).getWriter();
    }
}