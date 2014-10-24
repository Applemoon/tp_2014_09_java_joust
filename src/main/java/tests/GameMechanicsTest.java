package tests;

import base.GameMechanicsImpl;
import base.WebSocketServiceImpl;
import interfaces.GameMechanics;
import interfaces.WebSocketService;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameMechanicsTest {
    private GameMechanics gameMechanics;

    @Before
    public void setUp() throws Exception {
        gameMechanics = new GameMechanicsImpl(getWebSocketService());
    }

    @Test
    public void testAddUser() throws Exception {
        // TODO
    }

    @Test
    public void testIncrementScore() throws Exception {
        // TODO
    }

    @Test
    public void testRun() throws Exception {
        // TODO
    }

    private WebSocketService getWebSocketService() {
        return new WebSocketServiceImpl();
    }
}