package game;

/**
 * Created by applemoon on 30.10.14.
 */
public class GameCell {
    public enum CellState { FILLED_FIRST, FILLED_SECOND, EMPTY}
    private CellState state;

    public GameCell() {
        this.state = CellState.EMPTY;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }
}
