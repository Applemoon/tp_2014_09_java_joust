package tests.admin;

import admin.AdminPageServlet;
import db.UserProfileImpl;
import frontend.UserProfileServlet;
import interfaces.AccountService;
import interfaces.UserProfile;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class AdminPageServletTest {

    private AdminPageServlet adminPageServlet;

    private AccountService mockAccountService = mock(AccountService.class);
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private HttpSession mockSession;
    private PrintWriter mockPrintWriter;

    private String adminUserLogin = "admin";
    private String ordinaryUserLogin = "user";
    private String userPass = "k)O0asR$@18";
    private String userEmail = "kcuf@tihs.ru";
    private String sessionId = "sessionId";

    @Before
    public void setUp() throws Exception {
        adminPageServlet = new AdminPageServlet(mockAccountService);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        mockPrintWriter = mock(PrintWriter.class);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
    }

    @Test
    public void testDoGetWhenUserIsNotAdmin() throws Exception {
        UserProfile mockUser = createOrdinaryMockUser();
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.getUserProfile(sessionId)).thenReturn(mockUser);

        adminPageServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse,  atLeastOnce()).sendRedirect(UserProfileServlet.userProfilePageURL);
    }

    @Test
    public void testDoGetWhenUserIsAdmin() throws  Exception {
        UserProfile mockAdmin = createMockAdmin();
        when(mockSession.getId()).thenReturn(sessionId);
        when(mockAccountService.getUserProfile(sessionId)).thenReturn(mockAdmin);
        when(mockRequest.getParameter(anyString())).thenReturn(null);

        adminPageServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setContentType(anyString());
        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse, atLeastOnce()).getWriter();
        verify(mockPrintWriter, atLeastOnce()).println(anyString());
        verify(mockAccountService, atLeastOnce()).getAmountOfRegisteredUsers();
        verify(mockAccountService, atLeastOnce()).getAmountOfUsersOnline();
    }

    private UserProfile createSpyUser(String login) {
        UserProfile user = new UserProfileImpl(login, userPass, userEmail);
        return spy(user);
    }

    private UserProfile createOrdinaryMockUser() {
        return createSpyUser(ordinaryUserLogin);
    }

    private UserProfile createMockAdmin() {
        return createSpyUser(adminUserLogin);
    }
}