package frontend;

import interfaces.AccountService;
import interfaces.UserProfile;
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
 * Created by alexey on 26.09.14.
 */
public class UserProfileServletImpl extends HttpServlet implements UserProfileServlet {
    private AccountService accountService;

    public UserProfileServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

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
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", user.getLogin());
        pageVariables.put("email", user.getEmail());
        response.getWriter().println(PageGenerator.getPage("profile.tml", pageVariables));
    }
}
