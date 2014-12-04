package servlets;

import frontend.CustomWebSocketCreator;
import interfaces.services.AccountService;
import interfaces.services.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gameplay"}) // TODO узнать, что это такое
public class WebSocketGameServlet extends WebSocketServlet {
    public final static String gamePageURL = "/gameplay";
    private final static int IDLE_TIME = 60 * 1000;
    private final WebSocketService webSocketService;
    private final AccountService accountService;

    public WebSocketGameServlet(WebSocketService webSocketService,
                                AccountService accountService) {
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(webSocketService, accountService));
    }
}
