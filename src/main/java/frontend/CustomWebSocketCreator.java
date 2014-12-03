package frontend;

import db.UserProfile;
import interfaces.services.AccountService;
import interfaces.services.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class CustomWebSocketCreator implements WebSocketCreator {
    private final WebSocketService webSocketService;
    private final AccountService accountService;

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
