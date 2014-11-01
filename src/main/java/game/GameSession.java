package game;

import base.ClickResult;

public class GameSession {
    private final String first;
    private final String second;
    private GameField gameField;
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
        boolean firstUser = true;
        if (first.equals(userName)) {
            firstUser = true;
        }
        else if (second.equals(userName)) {
            firstUser = false;
        }
        else {
            System.err.println("GameSession.getEnemyName(): wrong 'userName' value");
            return ClickResult.NO_RESULT;
        }

        if (firstPlayerTurn && userName.equals(first)) {
            final ClickResult clickResult = gameField.clickCell(firstUser, x, y);
            if (clickResult == ClickResult.FIRST_FILLED) {
                firstPlayerTurn = false;
            }
            else if (clickResult == ClickResult.SECOND_FILLED) {
                firstPlayerTurn = true;
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

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public boolean isGameOver() {
        return gameIsOver;
    }
}
