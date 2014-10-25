package frontend;

import interfaces.AccountService;
import interfaces.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Created by alexey on 26.09.14.
 */
public class UserProfileServlet extends HttpServlet {
    public static final String userProfilePageURL = "/profile";
    private AccountService accountService;

    public UserProfileServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();

        if (!accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(SignInServlet.signInPageURL);
            return;
        }
        final String exit = request.getParameter("exit");
        if (exit != null) {
            accountService.logOut(sessionId);
            response.sendRedirect(SignInServlet.signInPageURL);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        UserProfile user = accountService.getUserProfile(sessionId);
        JSONObject pageVariables = new JSONObject();
        pageVariables.put("login", user.getLogin());
        pageVariables.put("email", user.getEmail());
        response.getWriter().println(pageVariables.toString());
    }
}
