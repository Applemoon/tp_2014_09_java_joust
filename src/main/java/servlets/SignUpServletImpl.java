package servlets;

import db.UserProfile;
import interfaces.services.AccountService;
import interfaces.servlets.SignUpServlet;
import messageSystem.ThreadSettings;
import utils.ProcessState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONObject;

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

        if (!login.isEmpty() && !password.isEmpty()) {
            UserProfile user = new UserProfile(login, password);
            accountService.signUp(user);
            boolean haveResponse = false;
            long millisecondsExpired = 0;
            Date date = new Date();
            long startTime = date.getTime();
            while (!haveResponse && millisecondsExpired < ThreadSettings.CLIENT_TIMEOUT) {
                final ProcessState signUpState = accountService.getSignUpState(login);
                switch (signUpState) {
                    case DoneOK:
                        response.setStatus(HttpServletResponse.SC_OK);
                        haveResponse = true;
                        break;
                    case DoneNotOK:
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        responseJson.put("msg", "already_signed_up");
                        haveResponse = true;
                        break;
                    case Error:
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        responseJson.put("msg", "unknown_error");
                        haveResponse = true;
                        break;
                    case Doing:
                    default:
                        break;
                }

                try {
                    Thread.sleep(ThreadSettings.CLIENT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                date = new Date();
                millisecondsExpired = date.getTime() - startTime;
            }

            if (!haveResponse) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                responseJson.put("msg", "unknown_error");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseJson.put("msg", "wrong_data");
        }

        response.getWriter().println(responseJson.toString());
    }
}
