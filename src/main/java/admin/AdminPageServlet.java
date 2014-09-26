package admin;

import main.AccountService;
import templater.PageGenerator;

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
public class AdminPageServlet extends HttpServlet {
    public static final String adminPageURL = "/admin";

    private AccountService accountService;

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset = utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        String timeString;
        timeString = request.getParameter("shutdown");
        if (timeString != null) {
            int timeMS = Integer.valueOf(timeString);
            System.out.print("Server will be down after: " + timeMS + " ms");
            TimeHelper.sleep(timeMS);
            System.out.print("\nShutdown");
            System.exit(0);
        }

        pageVariables.put("amountOfRegisteredUsers", accountService.getAmountOfRegisteredUsers());
        pageVariables.put("amountOfUsersOnline", accountService.getAmountOfUsersOnline());
        response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
    }
}
