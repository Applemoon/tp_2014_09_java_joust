package interfaces;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SignUpServlet {
    static final String signUpPageURL = "/signup";

    @SuppressWarnings("unused")
    void doPost(HttpServletRequest request,
                  HttpServletResponse response) throws ServletException, IOException;
}
