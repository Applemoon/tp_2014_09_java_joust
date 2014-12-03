package services;

import frontend.GameWebSocket;
import game.GameSession;
import interfaces.services.WebSocketService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class WebSocketServiceImpl implements WebSocketService {
    private final Map<String, GameWebSocket> userSockets = new HashMap<>();
    private final Queue<String> waitersQueue = new LinkedList<>();

    @Override
    public void addUserSocket(GameWebSocket userSocket) {
        final String userName = userSocket.getName();
        userSockets.put(userName, userSocket);

        if (!waitersQueue.isEmpty()) {
            final String waiterName = waitersQueue.remove();
            if (userName.equals(waiterName)) {
                waitersQueue.add(userName);
                return;
            }
            startGame(waiterName, userName);
            return;
        }
        waitersQueue.add(userName);
    }

    @Override
    public void notifyGameOver(String user, String winner) {
        userSockets.get(user).gameOverMessage(winner);
    }

    @Override
    public void notifyCellFilled(String user, int x, int y, String nameFilled) {
        userSockets.get(user).fillCellMessage(x, y, nameFilled);
    }

    @Override
    public void removeSocket(GameWebSocket userSocket) {
        userSockets.remove(userSocket.getName());
    }

    private void startGame(String first, String second) {
        GameSession gameSession = new GameSession(first, second);

        userSockets.get(first).setGameSession(gameSession);
        userSockets.get(second).setGameSession(gameSession);

        // Первым ходит тот игрок, который дольше ждет игру
        userSockets.get(first).startGameMessage(second, true);
        userSockets.get(second).startGameMessage(first, false);
    }

}
