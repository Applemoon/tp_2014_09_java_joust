package base;

import interfaces.GameMechanics;
import interfaces.WebSocketService;
import utils.TimeHelper;

import java.util.*;

public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;
    private static final int gameTime = 15 * 1000;
    private WebSocketService webSocketService;
    private Map<String, GameSession> nameToGame = new HashMap<>();
    private Set<GameSession> allSessions = new HashSet<>();
    private Queue<String> waitersQueue;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
        waitersQueue = new LinkedList<String>();
    }

    @Override
    public void addUser(String user) {
        if (!waitersQueue.isEmpty()) {
            final String secondUser = waitersQueue.remove();
            if (user == secondUser) {
                waitersQueue.add(user);
                return;
            }
            startGame(user, secondUser);
        }
        else {
            waitersQueue.add(user);
        }
    }

    @Override
    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                final boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                allSessions.remove(session);
            }
        }
    }

    private void startGame(String first, String second) {
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}
