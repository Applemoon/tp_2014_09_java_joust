package interfaces;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexey on 11.10.2014.
 */
public interface UserProfileServlet {
    static final String userProfilePageURL = "/profile";

    void doGet(HttpServletRequest request,
                  HttpServletResponse response) throws ServletException, IOException;
}