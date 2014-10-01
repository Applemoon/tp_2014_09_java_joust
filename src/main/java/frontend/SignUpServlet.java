package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 13.09.14.
 */
public class SignUpServlet extends HttpServlet  {

    public static final String signUpPageURL = "/signup";

    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

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
            pageVariables.put("answerFromServer", "Hello, " + accountService.getUserProfile(sessionId).getLogin() + "!");
        else
            pageVariables.put("answerFromServer", "");

        response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String email = request.getParameter("email");

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("url", signUpPageURL);

        boolean success = false;

        if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {
            pageVariables.put("answerFromServer", "All fields are required!");
        } 
        else {
            UserProfile user = new UserProfile(login, password, email);

            if (accountService.signUp(user)) {
                pageVariables.put("answerFromServer", "Greetings, " + login + ". You were successfully registered!");
                success = true;
            }
            else {
                pageVariables.put("answerFromServer", "Player with login " + login + " is already registered!");
            }

        }


        if (!success) {
            pageVariables.put("login", login);
            pageVariables.put("password", password);
            pageVariables.put("email", email);
        } 
        else {
            pageVariables.put("login", "");
            pageVariables.put("password", "");
            pageVariables.put("email", "");
        }

        response.getWriter().println(PageGenerator.getPage("signUp.tml", pageVariables));
    }
}
