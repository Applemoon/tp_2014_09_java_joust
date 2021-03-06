package servlets;

import interfaces.services.AccountService;
import interfaces.servlets.LogOutServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServletImpl extends HttpServlet implements LogOutServlet {
    private final AccountService accountService;

    public LogOutServletImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        accountService.logOut(sessionId);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
