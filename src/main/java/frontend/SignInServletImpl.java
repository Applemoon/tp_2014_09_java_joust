package frontend;

import interfaces.AccountService;
import interfaces.SignInServlet;
import interfaces.UserProfileServlet;

import utils.PageGenerator;

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
public class SignInServletImpl extends HttpServlet implements SignInServlet {
    private AccountService accountService;

    public SignInServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        final String sessionId = request.getSession().getId();
        if (accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(UserProfileServlet.userProfilePageURL);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = getPageVariables("", "");
        response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        if (!login.isEmpty() && !password.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (accountService.signIn(sessionId, login, password)) {
                response.sendRedirect(UserProfileServlet.userProfilePageURL);
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
