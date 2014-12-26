package interfaces.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AdminPageServlet {
    static final String adminPageURL = "/admin";

    @SuppressWarnings("unused")
    void doGet(HttpServletRequest request,
                  HttpServletResponse response) throws ServletException, IOException;
}
