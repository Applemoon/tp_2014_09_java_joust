package base;

import frontend.GameWebSocket;
import game.GameSession;
import interfaces.WebSocketService;

import java.util.*;

public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>(); // TODO сделать очистку карты
    private Queue<String> waitersQueue = new LinkedList<String>();

    @Override
    public void addUserSocket(GameWebSocket userSocket) {
        final String userName = userSocket.getName();
        userSockets.put(userName, userSocket);

        if (!waitersQueue.isEmpty()) {
            final String secondUserName = waitersQueue.remove();
            if (userName.equals(secondUserName)) {
                waitersQueue.add(userName);
                return;
            }
            startGame(userName, secondUserName);
            return;
        }
        waitersQueue.add(userName);
    }

    @Override
    public void notifyGameOver(String user, boolean win) {
        userSockets.get(user).gameOver(win);
    }

    @Override
    public void removeSocket(GameWebSocket userSocket) {
        userSockets.remove(userSocket.getName());
    }

    private void startGame(String first, String second) {
        GameSession gameSession = new GameSession(first, second);

        userSockets.get(first).setGameSession(gameSession);
        userSockets.get(second).setGameSession(gameSession);

        userSockets.get(first).startGame(second);
        userSockets.get(second).startGame(first);
    }

}
