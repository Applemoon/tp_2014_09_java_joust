package game;

/**
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
    private final int fieldSize;
    private final int smallEdge;
    private final int bigEdge;
    private final int cellsCount;
    private int cellsFilled = 0;
    private final int chainToWin;
    private final GameCell[][] cells;
    private enum Direction { VERTICAL, RIGHT_UP, LEFT_UP }

    public GameField(int fieldSize, int chainToWin) {
        this.fieldSize = fieldSize;
        this.chainToWin = chainToWin;

        smallEdge = (fieldSize - 3)/2;
        bigEdge = (3*fieldSize - 1)/2;
        cellsCount = fieldSize * fieldSize - 2 * (triangleSum((fieldSize - 1) / 2));

        cells = new GameCell[this.fieldSize][this.fieldSize];
        for (int i = 0; i < this.fieldSize; i++) {
            for (int j = 0; j < this.fieldSize; j++) {
                cells[i][j] = new GameCell();
            }
        }
    }

    private int triangleSum(int n) {
        int m = n - 1;
        while (m != 0) {
            n += m;
            m--;
        }

        return n;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public ClickResult clickCell(boolean firstPlayer, int x, int y) {
        if (notValidCoord(x, y)) {
            return ClickResult.NO_RESULT;
        }

        if (alreadyFilled(x, y)) {
            return ClickResult.NO_RESULT;
        }

        if (firstPlayer) {
            cells[x][y].setState(GameCell.CellState.FILLED_FIRST);
            cellsFilled++;
        } else {
            cells[x][y].setState(GameCell.CellState.FILLED_SECOND);
            cellsFilled++;
        }

        final ClickResult checkWinResult = checkWin(x, y);
        if (checkWinResult == ClickResult.WIN) {
            return ClickResult.WIN;
        }

        final ClickResult checkStandOffResult = checkStandOff();
        if (checkStandOffResult == ClickResult.STANDOFF) {
            return ClickResult.STANDOFF;
        }

        return ClickResult.FILLED;
    }

    private ClickResult checkStandOff() {
        if (cellsFilled >= cellsCount) {
            return ClickResult.STANDOFF;
        }
        return ClickResult.NO_RESULT;
    }

    private ClickResult checkWin(int x, int y) {
        final boolean firstPlayer = (cells[x][y].getState() == GameCell.CellState.FILLED_FIRST);
        int step = 1;
        int chain = 1;
        int curX = x;
        int curY = y;
        Direction direction = Direction.VERTICAL;

        while (true) {
            switch (direction) {
                case VERTICAL:
                    curX += step;
                    break;
                case RIGHT_UP:
                    curY += step;
                    break;
                case LEFT_UP:
                    curX += step;
                    curY -= step;
                    break;
            }

            // проверки клетки
            if (!notValidCoord(curX, curY)) {
                if ((firstPlayer &&
                        cells[curX][curY].getState() == GameCell.CellState.FILLED_FIRST) ||
                    (!firstPlayer &&
                            cells[curX][curY].getState() == GameCell.CellState.FILLED_SECOND)) {
                    chain++;

                    if (chain >= chainToWin) {
                        return ClickResult.WIN;
                    }

                    continue;
                }
            }

            // ряд закончился
            if (step == -1) {
                // смена направления (не на обратное)
                direction = nextDirection(direction);
                if (direction == Direction.VERTICAL) {
                    break;
                }
                chain = 1;
            }

            curX = x;
            curY = y;
            step *= -1;
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

    private boolean notValidCoord(int x, int y) {
        return (x < 0              ||
                x >= fieldSize     ||
                y < 0              ||
                y >= fieldSize     ||
                x + y <= smallEdge ||
                x + y >= bigEdge);
    }

    private boolean alreadyFilled(int x, int y) {
        return (cells[x][y].getState() == GameCell.CellState.FILLED_FIRST ||
                cells[x][y].getState() == GameCell.CellState.FILLED_SECOND);
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