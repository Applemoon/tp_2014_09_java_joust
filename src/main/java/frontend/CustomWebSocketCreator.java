package frontend;

import interfaces.AccountService;
import interfaces.UserProfile;
import interfaces.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by applemoon on 14.10.14.
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private WebSocketService webSocketService;
    private AccountService accountService;

    public CustomWebSocketCreator(WebSocketService webSocketService,
                                  AccountService accountService) {
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest request, ServletUpgradeResponse response) {
        final String sessionId = request.getHttpServletRequest().getSession().getId();
        final UserProfile userProfile = accountService.getUserProfile(sessionId);
        final String name = userProfile.getLogin();
        return new GameWebSocket(name, webSocketService);
    }
}
