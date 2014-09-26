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
                      HttpServletResponse response)
            throws ServletException, IOException {

        String sessionId = request.getSession().getId();
        if (accountService.isLoggedIn(sessionId)) {
            response.sendRedirect(UserProfileServlet.UserProfilePageURL);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("url", signInPageURL);
            pageVariables.put("answerFromServer", "");
            pageVariables.put("login", "");
            response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == "" || password == "") {
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("url", signInPageURL);
            pageVariables.put("answerFromServer", "Not all fields are filled!");
            pageVariables.put("login", login);
            response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
        } else {
            String sessionId = request.getSession().getId();
            if (accountService.signIn(sessionId, login, password)) {
                response.sendRedirect(UserProfileServlet.UserProfilePageURL);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("url", signInPageURL);
                pageVariables.put("answerFromServer", "Player with that combination of login and password was not found!");
                pageVariables.put("login", login);
                response.getWriter().println(PageGenerator.getPage("signIn.tml", pageVariables));
            }
        }
    }
}
