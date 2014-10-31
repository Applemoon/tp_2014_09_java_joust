package frontend;

import base.ClickResult;
import game.GameSession;
import interfaces.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

@WebSocket
public class GameWebSocket {
    private String name;
    private Session session;
    private GameSession gameSession;
    private WebSocketService webSocketService;

    public GameWebSocket(String name, WebSocketService webSocketService) {
        this.name = name;
        this.webSocketService = webSocketService;
    }

    public String getName() {
        return name;
    }

    private void sendJSON(JSONObject object) {
        try {
            session.getRemote().sendString(object.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void startGame(String enemyName) {
        JSONObject json = new JSONObject();
        json.put("status", "start");
        json.put("enemyName", enemyName);
//        json.put(); TODO кто первый ходит
        sendJSON(json);
    }

    public void gameOver(boolean win) {
        JSONObject json = new JSONObject();
        json.put("status", "finish");
        json.put("win", win);
        sendJSON(json);
    }

    public void fillCell(boolean isFirst) {
        JSONObject json = new JSONObject();
        json.put("status", "fillCell");
        json.put("player", isFirst);
        sendJSON(json);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        // TODO делать разное в зависимости от data
        System.out.println(data); // TODO убрать
        final ClickResult clickResult = gameSession.clickCell(name, 1, 1); // TODO
        if (clickResult == ClickResult.WIN) {
            gameOver(true);
            final String enemyName = gameSession.getEnemyName(name);
            webSocketService.notifyGameOver(enemyName, false);
        }
        else if (clickResult == ClickResult.FIRST_FILLED) {
            fillCell(true);
        }
        else if (clickResult == ClickResult.SECOND_FILLED) {
            fillCell(false);
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        webSocketService.addUserSocket(this);
    }

//    @OnWebSocketClose
//    public void onClose(int statusCode, String reason) {
//
//    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}
