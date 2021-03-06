package servlets;

import db.UserProfile;
import interfaces.services.AccountService;
import utils.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontendServlet extends HttpServlet {
    private final AccountService accountService;
    public static final String frontendUrl = "/game.html";

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

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
