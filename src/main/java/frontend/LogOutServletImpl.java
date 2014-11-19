package frontend;

import interfaces.AccountService;
import interfaces.LogOutServlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by applemoon on 19.11.14.
 */
public class LogOutServletImpl extends HttpServlet implements LogOutServlet {
    private AccountService accountService;

    public LogOutServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login"); // TODO нужен?
        final String sessionId = request.getSession().getId();

        JSONObject responseJson = new JSONObject();

        accountService.logOut(sessionId);

        responseJson.put("status", 200);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(responseJson.toString());
    }
}
