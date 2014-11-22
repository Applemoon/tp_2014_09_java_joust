package game;

import base.ClickResult;

public class GameSession {
    private final String first;
    private final String second;
    private final GameField gameField;
    private boolean firstPlayerTurn;
    private boolean gameIsOver;

    public GameSession(String user1, String user2) {
        first = user1;
        second = user2;

        gameField = new GameField();

        firstPlayerTurn = true;
        gameIsOver = false;
    }

    public ClickResult clickCell(String userName, int x, int y) {
        if ((firstPlayerTurn  && userName.equals(first)) ||
            (!firstPlayerTurn && userName.equals(second))) {
            final boolean firstUser = first.equals(userName);
            final ClickResult clickResult = gameField.clickCell(firstUser, x, y);
            if (clickResult == ClickResult.FILLED) {
                firstPlayerTurn = !firstPlayerTurn;
            }
            else if (clickResult == ClickResult.WIN) {
                gameIsOver = true;
            }

            return clickResult;
        }

        return ClickResult.NO_RESULT;
    }

    public String getEnemyName(String userName) {
        if (first.equals(userName)) {
            return second;
        }
        else if (second.equals(userName)) {
            return first;
        }

        System.err.println("GameSession.getEnemyName(): wrong 'userName' value");
        return "";
    }

    public boolean isGameOver() {
        return gameIsOver;
    }

    public int getFieldSize() {
        return gameField.getFieldSize();
    }
}
