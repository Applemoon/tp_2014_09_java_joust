package admin;

import frontend.UserProfileServlet;
import interfaces.AccountService;
import interfaces.UserProfile;
import utils.TimeHelper;

import org.json.simple.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    public static final String adminPageURL = "/admin";
    private AccountService accountService;

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        UserProfile user = accountService.getUserProfile(sessionId);
        if (user != null && user.getLogin().equals("admin")) {
            response.setContentType("text/html; charset = utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject pageVariables = new JSONObject();
            final String timeString = request.getParameter("shutdown");
            if (timeString != null) {
                final int timeMS = Integer.valueOf(timeString);
                System.out.print("Server will be down after: " + timeMS + " ms");
                TimeHelper.sleep(timeMS);
                System.out.print("\nShutdown");
                System.exit(0);
            }

            pageVariables.put("amountOfRegisteredUsers", accountService.getAmountOfRegisteredUsers());
            pageVariables.put("amountOfUsersOnline", accountService.getAmountOfUsersOnline());
            response.getWriter().println(pageVariables.toString());
            return;
        }

        response.sendRedirect(UserProfileServlet.userProfilePageURL);
    }
}
