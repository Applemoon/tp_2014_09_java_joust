package servlets;

import interfaces.services.AccountService;
import interfaces.servlets.SignInServlet;
import org.json.simple.JSONObject;
import utils.ProcessState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServletImpl extends HttpServlet implements SignInServlet {
    private final AccountService accountService;

    public SignInServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String sessionId = request.getSession().getId();

        JSONObject responseJson = new JSONObject();

        if (login.isEmpty() || password.isEmpty() || !accountService.validLoginAndPass(login, password)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseJson.put("msg", "wrong_data");
        } else if (accountService.signIn(sessionId, login)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                responseJson.put("msg", "already_signed_in");
            }

        response.getWriter().println(responseJson.toString());
    }
}
