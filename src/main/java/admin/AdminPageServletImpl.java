package admin;

import interfaces.UserProfileServlet;
import interfaces.AccountService;
import interfaces.AdminPageServlet;
import interfaces.UserProfile;

import utils.PageGenerator;
import utils.TimeHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 25.09.14.
 */
public class AdminPageServletImpl extends HttpServlet implements AdminPageServlet {
    private AccountService accountService;

    public AdminPageServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        UserProfile user = accountService.getUserProfile(sessionId);
        if (user != null && user.getLogin().equals("admin")) {
            response.setContentType("text/html; charset = utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Object> pageVariables = new HashMap<>();
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
            response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
        }
        else
            response.sendRedirect(UserProfileServlet.userProfilePageURL);
    }
}