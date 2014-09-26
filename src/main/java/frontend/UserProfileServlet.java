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
 * Created by alexey on 26.09.14.
 */
public class UserProfileServlet extends HttpServlet {

    public static final String UserProfilePageURL = "/profile";

    private AccountService accountService;

    public UserProfileServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();

        if (!accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(SignInServlet.signInPageURL);
        } else {
            String exit = request.getParameter("exit");
            if (exit != null) {
                accountService.logOut(sessionId);
                response.sendRedirect(SignInServlet.signInPageURL);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                UserProfile user = accountService.getUserProfile(sessionId);
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("login", user.getLogin());
                pageVariables.put("email", user.getEmail());
                response.getWriter().println(PageGenerator.getPage("profile.tml", pageVariables));
            }
        }
    }
}
