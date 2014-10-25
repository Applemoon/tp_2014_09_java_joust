package frontend;

import interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * @author alexey
 */
public class SignInServletImpl extends HttpServlet {
    public final static String signInPageURL = "/api/v1/auth/signin";
    private AccountService accountService;

    public SignInServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        final String sessionId = request.getSession().getId();
        if (accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(UserProfileServletImpl.userProfilePageURL);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject pageVariables = getPageVariables("", "");
        response.getWriter().println(pageVariables.toString());
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        if (!login.isEmpty() && !password.isEmpty()) {
            final String sessionId = request.getSession().getId();
            if (accountService.signIn(sessionId, login, password)) {
                response.sendRedirect(UserProfileServletImpl.userProfilePageURL);
                return;
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject pageVariables = getPageVariables("Wrong login or password!", login);
            response.getWriter().println(pageVariables.toString());
            return;
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject pageVariables = getPageVariables("All fields are required!", login);
        response.getWriter().println(pageVariables.toString());
    }

    private JSONObject getPageVariables(String answer, String login) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("url", signInPageURL);
        resultJson.put("answerFromServer", answer);
        resultJson.put("login", login);
        return resultJson;
    }
}
