package admin;

import interfaces.AccountService;
import interfaces.AdminPageServlet;
import interfaces.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServletImpl extends HttpServlet implements AdminPageServlet {
    private AccountService accountService;

    public AdminPageServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        UserProfile user = accountService.getUserProfile(sessionId);
        if (user != null && user.getLogin().equals("admin")) {
            // TODO реализовать функционал (потом)
        }
    }
}
