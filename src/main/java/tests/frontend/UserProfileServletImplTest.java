package frontend;

import frontend.UserProfileServletImpl;
import interfaces.AccountService;
import interfaces.SignInServlet;
import interfaces.UserProfile;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class UserProfileServletImplTest {

    private UserProfileServletImpl userProfileServlet;

    private String sessionId = "sessionId";
    private String userLogin = "login";
    private String userPass = "pass@_SuPerPASs42";
    private String userEmail = "email@email.com";

    private AccountService mockAccountService = mock(AccountService.class);

    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private HttpSession mockSession;

    @Before
    public void setUp() throws Exception {
        userProfileServlet = new UserProfileServletImpl(mockAccountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);

        when(mockRequest.getSession()).thenReturn(mockSession);
    }

    @Test
    public void testDoGetWithoutLogin() throws Exception {
        when(mockSession.getId()).thenReturn(sessionId + sessionId);

        userProfileServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).sendRedirect(SignInServlet.signInPageURL);
    }

    @Test
    public void testDoGetWithExit() throws Exception {
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.isLoggedIn(sessionId)).thenReturn(true);
        when(mockRequest.getParameter("exit")).thenReturn("not null string");

        userProfileServlet.doGet(mockRequest, mockResponse);

        verify(mockAccountService, atLeastOnce()).logOut(sessionId);
        verify(mockResponse, atLeastOnce()).sendRedirect(SignInServlet.signInPageURL);
    }

    @Test
    public void testDoGetWithLogin() throws Exception {
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.isLoggedIn(sessionId)).thenReturn(true);
        UserProfile mockUser = createMockUser();
        when(mockAccountService.getUserProfile(sessionId)).thenReturn(mockUser);
        PrintWriter mockPrintWriter = mock(PrintWriter.class);
        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

        userProfileServlet.doGet(mockRequest, mockResponse);

        verify(mockAccountService, atLeastOnce()).getUserProfile(sessionId);
        verify(mockUser, atLeastOnce()).getLogin();
        verify(mockUser, atLeastOnce()).getEmail();
    }

    private UserProfile createMockUser() {
        UserProfile user = mock(UserProfile.class);
        when(user.getLogin()).thenReturn(userLogin);
        when(user.getPass()).thenReturn(userPass);
        when(user.getEmail()).thenReturn(userEmail);
        return user;
    }

}