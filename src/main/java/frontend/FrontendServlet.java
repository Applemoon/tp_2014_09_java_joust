package frontend;

import interfaces.AccountService;
import interfaces.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.simple.JSONObject;

public class FrontendServlet extends HttpServlet {
    private AccountService accountService;

    public FrontendServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        final UserProfile user = accountService.getUserProfile(sessionId);
        if (user == null) {
            response.sendRedirect(SignInServletImpl.signInPageURL);
            return;
        }
        final String name = user.getLogin();
        final String safeName = (name == null) ? "NoName" : name;

        JSONObject pageVariables = new JSONObject();
        pageVariables.put("myName", safeName);

        response.getWriter().println(pageVariables.toString());

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
