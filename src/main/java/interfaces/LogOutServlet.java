package interfaces;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by applemoon on 19.11.14.
 */
public interface LogOutServlet {
    static final String logOutPageUrl = "/api/v1/auth/logout";

    void doPost(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException;
}
