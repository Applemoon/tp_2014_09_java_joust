package servlets;

import db.UserProfile;
import interfaces.services.AccountService;
import interfaces.servlets.AdminPageServlet;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
        final String shutdownTimeParameter = request.getParameter("shutdown");
        UserProfile user = accountService.getUserProfile(sessionId);

        if (user == null || !user.getLogin().equals("admin")) {
            return;
        }

        if (shutdownTimeParameter != null) {
            int shutdownTime = Integer.parseInt(shutdownTimeParameter);
            Timer timer = new Timer();
            System.out.println("Server shutdown in " + shutdownTime/1000 + " seconds.");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Server shutdown now.");
                    System.exit(0);
                }
            }, shutdownTime);
        }

        // TODO реализовать функционал (потом)
        JSONObject responseJson = new JSONObject();
        responseJson.put("users_count", accountService.getAmountOfRegisteredUsers());
        responseJson.put("users_online_count", accountService.getAmountOfUsersOnline());
        response.getWriter().println(responseJson.toString());
    }
}
