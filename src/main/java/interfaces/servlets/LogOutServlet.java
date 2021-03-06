package interfaces.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LogOutServlet {
    static final String logOutPageUrl = "/logout";

    @SuppressWarnings("unused")
    void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException;
}
