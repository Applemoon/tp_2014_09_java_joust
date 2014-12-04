package frontend;

import interfaces.services.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.junit.Before;
import org.junit.Test;
import services.WebSocketServiceImpl;

import static org.junit.Assert.*;

public class GameWebSocketTest {
    private GameWebSocket socket;
    private WebSocketService webSocketService;

    @Before
    public void setUp() throws Exception {
        webSocketService = new WebSocketServiceImpl();
        socket = new GameWebSocket(getName(), webSocketService);
    }

    private String getName() {
        return "username";
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(getName(), socket.getName());
    }

    @Test
    public void testStartGameMessage() throws Exception {
        // TODO
    }

    @Test
    public void testGameOverMessage() throws Exception {
        // TODO
    }

    @Test
    public void testFillCellMessage() throws Exception {
        // TODO
    }

    @Test
    public void testOnMessage() throws Exception {
        // TODO
    }

    @Test
    public void testOnOpen() throws Exception {
        // TODO
    }

    @Test
    public void testOnClose() throws Exception {
        // TODO
    }

    @Test
    public void testSetGameSession() throws Exception {
        // TODO
    }
}