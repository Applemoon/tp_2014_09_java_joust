package game;

import base.ClickResult;

/**
 * Created by applemoon on 30.10.14.
 *
 * Вот такое игрвое поле:
 *       0
 *      /
 *     0 0 0 . .
 *    0 0 0 0 .
 *   0 0 0 0 0
 *  . 0 0 0 0
 * . . 0 0 0 -> 0
 */
public class GameField {
    private static final int fieldSize = 5; // Для ровного шестиугольного поля только нечетные значения
    private static final int chainToWin = 3;
    private GameCell[][] cells = new GameCell[fieldSize][fieldSize];
    private enum Direction { VERTICAL, RIGHT_UP, LEFT_UP };

    public GameField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                cells[i][j] = new GameCell();
            }
        }
    }

    public ClickResult clickCell(boolean firstPlayer, int x, int y) {
        if (validCoord(x, y)) {
            return ClickResult.NO_RESULT;
        }

        if (cells[x][y].getState() == GameCell.CellState.FILLED_FIRST ||
            cells[x][y].getState() == GameCell.CellState.FILLED_SECOND) {
            return ClickResult.NO_RESULT;
        }

        if (firstPlayer) {
            cells[x][y].setState(GameCell.CellState.FILLED_FIRST);
        }
        else {
            cells[x][y].setState(GameCell.CellState.FILLED_SECOND);
        }

        final ClickResult checkWinResult = checkWin(x, y);
        if (checkWinResult != ClickResult.NO_RESULT) {
            return checkWinResult;
        }

        if (firstPlayer) {
            return ClickResult.FIRST_FILLED;
        }
        return ClickResult.SECOND_FILLED;
    }

    private ClickResult checkWin(int x, int y) {
        // TODO проверить
        final boolean firstPlayer = (cells[x][y].getState() == GameCell.CellState.FILLED_FIRST);
        int step = 1;
        int chain = 1;
        int curX = x;
        int curY = y;
        Direction direction = Direction.VERTICAL;

        while (true) {
            switch (direction) {
                case VERTICAL:
                    curY += step;
                    break;
                case RIGHT_UP:
                    curX += step;
                    break;
                case LEFT_UP:
                    curX -= step;
                    curY += step;
                    break;
            }

            if (validCoord(curX, curY) &&
                    (firstPlayer && cells[curX][curY].getState() == GameCell.CellState.FILLED_FIRST) ||
                    (!firstPlayer && cells[curX][curY].getState() == GameCell.CellState.FILLED_SECOND)) {
                chain++;

                if (chain >= chainToWin) {
                    return ClickResult.WIN;
                }
                continue;
            }

            if (step == 1) {
                step = -1;
                curX = x;
                curY = y;
                continue;
            }

            direction = nextDirection(direction);
            if (direction == Direction.VERTICAL) {
                break;
            }
        }

        return ClickResult.NO_RESULT;
    }

    private Direction nextDirection(Direction direction) {
        switch (direction) {
            case VERTICAL:
                return Direction.RIGHT_UP;
            case RIGHT_UP:
                return Direction.LEFT_UP;
        }

        return Direction.VERTICAL;
    }

    private boolean validCoord(int x, int y) {
        final int smallEdge = (fieldSize - 3)/2;
        final int bigEdge = (3*fieldSize - 1)/2;
        return (x < 0              ||
                x >= fieldSize     ||
                y < 0              ||
                y >= fieldSize     ||
                x + y <= smallEdge ||
                x + y >= bigEdge);
    }
}


/*
w - ширина поля, нечетная (дано)
s(w) = (w+1)/2 - длина грани
a < x+y < b
a(w, s) = w - s - 1
b(b1, b2) = b1 + b2
b1(w) = w - 1
b2(s) = s
-> b = s + w - 1
-> w - s - 1 < x+y < s + w - 1
-> w - (w+1)/2 - 1 < x+y < (w+1)/2 + w - 1
-> w - w/2 - 0.5 - 1 < x+y < 1.5w + 0.5 - 1
-> w/2 - 1.5 < x+y < 1.5w - 0.5


                  0 0 0 0 0 . . . .
                 0 0 0 0 0 0 . . .
                0 0 0 0 0 0 0 . .
               0 0 0 0 0 0 0 0 .       3 < x + y < 13 (8+5, 7+6, 6+7, 5+6)
              0 0 0 0 0 0 0 0 0
             . 0 0 0 0 0 0 0 0
            . . 0 0 0 0 0 0 0
           . . . 0 0 0 0 0 0
          . . . . 0 0 0 0 0        9x9 (5x5x5)


               0 0 0 0 . . .
              0 0 0 0 0 . .
             0 0 0 0 0 0 .       2 < x + y < 10 (6+4, 5+5, 4+6)
            0 0 0 0 0 0 0
           . 0 0 0 0 0 0
          . . 0 0 0 0 0
         . . . 0 0 0 0       7x7 (4x4x4)


              0 0 0 . .
            0 0 0 0 .      1 < x + y < 7 (4+3, 3+4)
           0 0 0 0 0
          . 0 0 0 0
         . . 0 0 0      5x5 (3x3x3)


           0 0 .    0 < x + y < 4 (2+2)
          0 0 0
         . 0 0    3x3 (2x2x2)
 */