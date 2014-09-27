package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexey
 */
public class SignInServlet extends HttpServlet {
    public static final String signInPageURL = "/api/v1/auth/signin";
    private AccountService accountService;


    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        if (accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(UserProfileServlet.UserProfilePageURL);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = getPageVariables("", "");
        response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        if (!login.isEmpty() && !password.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (accountService.signIn(sessionId, login, password)) {
                response.sendRedirect(UserProfileServlet.UserProfilePageURL);
                return;
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Object> pageVariables = getPageVariables("Wrong login or password!", login);
            response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
            return;
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = getPageVariables("All fields are required!", login);
        response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));        
    }


    private Map<String, Object> getPageVariables(String answer, String login) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("url", signInPageURL);
        pageVariables.put("answerFromServer", answer);
        pageVariables.put("login", login);
        return pageVariables;
    }
}
