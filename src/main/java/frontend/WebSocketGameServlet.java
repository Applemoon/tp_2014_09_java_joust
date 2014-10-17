package frontend;

import interfaces.AccountService;
import interfaces.GameMechanics;
import interfaces.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/game"})
public class WebSocketGameServlet extends WebSocketServlet {
    public final static String gamePageURL = "/gameplay";

    private final static int IDLE_TIME = 60 * 1000;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private AccountService accountService;

    public WebSocketGameServlet(GameMechanics gameMechanics,
                                WebSocketService webSocketService,
                                AccountService accountService) {
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(gameMechanics, webSocketService, accountService));
    }
}
