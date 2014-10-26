package frontend;

import interfaces.AccountService;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class SignUpServletTest {

    private SignUpServlet signUpServlet;

    private AccountService mockAccountService = mock(AccountService.class);
    private HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    private HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    private HttpSession mockSession = mock(HttpSession.class);
    private PrintWriter mockWriter = mock(PrintWriter.class);

    @Before
    public void setUp() throws Exception {
        signUpServlet = new SignUpServlet(mockAccountService);

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
    }

    @Test
    public void testDoGet() throws Exception {
        signUpServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }

    @Test
    public void testDoPost() throws Exception {
        when(mockRequest.getParameter("login")).thenReturn("login");
        when(mockRequest.getParameter("password")).thenReturn("passOrd");
        when(mockRequest.getParameter("email")).thenReturn("kcuf@tihs.ru");

        signUpServlet.doPost(mockRequest, mockResponse);

        verify(mockResponse, atLeastOnce()).setStatus(HttpServletResponse.SC_OK);
        verify(mockWriter, atLeastOnce()).println(anyString());
    }
}