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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebSocket
public class GameWebSocket {
    private final String name;
    private Session session;
    private GameSession gameSession;
    private final WebSocketService webSocketService;

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

    @SuppressWarnings("unchecked")
    public void startGameMessage(String enemyName, boolean hasFirstTurn) {
        JSONObject json = new JSONObject();
        json.put("type", "start");
        json.put("enemy", enemyName);
        json.put("field_size", gameSession.getFieldSize());
        if (hasFirstTurn) {
            json.put("player_turn", 1);
        } else {
            json.put("player_turn", 2);
        }
        sendJSON(json);
    }

    @SuppressWarnings("unchecked")
    public void gameOverMessage(String winner) {
        JSONObject json = new JSONObject();
        json.put("type", "win");
        json.put("winner", winner);
        sendJSON(json);
    }

    @SuppressWarnings("unchecked")
    void fillCellMessage(int x, int y, String player) {
        JSONObject json = new JSONObject();
        json.put("type", "turn");
        json.put("x", x);
        json.put("y", y);
        json.put("player", player);
        sendJSON(json);
    }

    @OnWebSocketMessage
    @SuppressWarnings("unused")
    public void onMessage(String data) throws ParseException {
        final JSONObject json = (JSONObject) new JSONParser().parse(data);
        final int x = Integer.parseInt(json.get("x").toString());
        final int y = Integer.parseInt(json.get("y").toString());

        final ClickResult clickResult = gameSession.clickCell(name, x, y);

        switch (clickResult) {
            case WIN:
                gameOverMessage(name);
                final String enemyName = gameSession.getEnemyName(name);
                webSocketService.notifyGameOver(enemyName, name);
                return;
            case FILLED:
                fillCellMessage(x, y, name);
                // TODO отправлять сообщение врагу
                return;
            case NO_RESULT:
            default:
                break;
        }
    }

    @OnWebSocketConnect
    @SuppressWarnings("unused")
    public void onOpen(Session session) {
        this.session = session;
        webSocketService.addUserSocket(this);
    }

    @OnWebSocketClose
    @SuppressWarnings("unused")
    public void onClose(int statusCode, String reason) {
        if (gameSession.isGameOver()) {
            webSocketService.removeSocket(this);
        }
        // TODO отправлять другому игроку сообщение
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}
