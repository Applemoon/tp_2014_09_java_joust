package game;

import java.io.Serializable;

public class GameSettings implements Serializable {
    private final int fieldSize;
    private final int chainToWin;

    public GameSettings() {
        fieldSize = 0;
        chainToWin = 0;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getChainToWin() {
        return chainToWin;
    }
}
