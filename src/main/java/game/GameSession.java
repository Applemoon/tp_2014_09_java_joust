package game;

import base.ClickResult;

public class GameSession {
    private final GameUser first;
    private final GameUser second;
    private GameField gameField;
    private boolean firstPlayerTurn;

    public GameSession(String user1, String user2) {
        first = new GameUser(user1);
//        first.setEnemyName(user2);

        second = new GameUser(user2);
//        second.setEnemyName(user1);

        gameField = new GameField();

        firstPlayerTurn = true;
    }

    public ClickResult clickCell(String userName, int x, int y) {
        boolean firstUser = true;
        if (first.getName().equals(userName)) {
            firstUser = true;
        }
        else if (second.getName().equals(userName)) {
            firstUser = false;
        }
        else {
            // TODO БИГ ЭРРОР
        }

        if (firstPlayerTurn && userName.equals(first.getName())) {
            final ClickResult clickResult = gameField.clickCell(firstUser, x, y);
            if (clickResult == ClickResult.FIRST_FILLED) {
                firstPlayerTurn = false;
            }
            else if (clickResult == ClickResult.SECOND_FILLED) {
                firstPlayerTurn = true;
            }

            return clickResult;
        }

        return ClickResult.NO_RESULT;
    }

    public String getEnemyName(String userName) {
        if (first.getName().equals(userName)) {
            return second.getName();
        }
        else if (second.getName().equals(userName)) {
            return first.getName();
        }

        // TODO БИГ ЭРРОР
        return "";
    }
}
