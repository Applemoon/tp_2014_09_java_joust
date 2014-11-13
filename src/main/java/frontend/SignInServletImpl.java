package frontend;

import interfaces.AccountService;
import interfaces.SignInServlet;

import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author alexey
 */
public class SignInServletImpl extends HttpServlet implements SignInServlet {
    private AccountService accountService;

    public SignInServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String email = "vasya_nagibator@mail.ru"; // TODO подключить БД

        final String sessionId = request.getSession().getId();
        JSONObject responseJson = new JSONObject();

        if (login.isEmpty() || password.isEmpty() || !accountService.validLoginAndPass(login, password)) {
            responseJson.put("status", 403);
            responseJson.put("msg", "wrong_data");
        } else if (accountService.signIn(sessionId, login, password)) {
            responseJson.put("status", 200);
            responseJson.put("email", email);
        } else {
            responseJson.put("status", 403);
            responseJson.put("msg", "already_signed_in");
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseJson.toString());
    }
}
