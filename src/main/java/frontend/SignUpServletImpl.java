package frontend;

import interfaces.AccountService;
import interfaces.SignInServlet;
import interfaces.SignUpServlet;
import interfaces.UserProfile;

import db.UserProfileImpl;
import utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 13.09.14.
 */
public class SignUpServletImpl extends HttpServlet implements SignUpServlet {
    private AccountService accountService;

    public SignUpServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("url", signUpPageURL);
        pageVariables.put("login", "");
        pageVariables.put("password", "");
        pageVariables.put("email", "");

        final String sessionId = request.getSession().getId();
        if (accountService.isLoggedIn(sessionId))
            // TODO написать пограмотнее
            pageVariables.put("answerFromServer", "You are logged as " + accountService.getUserProfile(sessionId).getLogin() + ".");
        else
            pageVariables.put("answerFromServer", "");

        response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String email = request.getParameter("email");

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();

        if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {
            pageVariables.put("answerFromServer", "All fields are required!");
            pageVariables.put("login", login);
            pageVariables.put("password", password);
            pageVariables.put("email", email);
            pageVariables.put("url", signUpPageURL);
            response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
            return;
        }

        UserProfile user = new UserProfileImpl(login, password, email);
        if (accountService.signUp(user)) {
            pageVariables.put("answerFromServer", "Greetings, " + login + ". You were successfully registered!");
            pageVariables.put("login", login);
            pageVariables.put("password", password);
            pageVariables.put("email", email);
            pageVariables.put("url", SignUpServlet.signUpPageURL);
            response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
            return;
        }

        pageVariables.put("answerFromServer", "Player with login " + login + " is already registered!");
        pageVariables.put("login", "");
        pageVariables.put("password", "");
        pageVariables.put("email", email);
        pageVariables.put("url", signUpPageURL);
        response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
    }
}
