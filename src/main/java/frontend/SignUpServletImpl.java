package frontend;

import interfaces.AccountService;
import interfaces.SignUpServlet;

import db.UserProfile;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServletImpl extends HttpServlet implements SignUpServlet {
    private final AccountService accountService;

    public SignUpServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        JSONObject responseJson = new JSONObject();

        if (login.isEmpty() || password.isEmpty()) {
            responseJson.put("status", 403);
            responseJson.put("msg", "wrong_data");
        } else {
            UserProfile user = new UserProfile(login, password);
            if (accountService.signUp(user)) {
                responseJson.put("status", 200);
            } else {
                responseJson.put("status", 403);
                responseJson.put("msg", "already_signed_up");
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseJson.toString());
    }
}
