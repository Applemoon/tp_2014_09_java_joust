package admin;

import interfaces.AccountService;
import interfaces.AdminPageServlet;
import interfaces.UserProfile;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServletImpl extends HttpServlet implements AdminPageServlet {
    private final AccountService accountService;

    public AdminPageServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        UserProfile user = accountService.getUserProfile(sessionId);
        if (user != null && user.getLogin().equals("admin")) {
            // TODO реализовать функционал (потом)
            JSONObject responseJson = new JSONObject();
            responseJson.put("email", "admin");
            response.getWriter().println(responseJson.toString());
        }
    }
}
