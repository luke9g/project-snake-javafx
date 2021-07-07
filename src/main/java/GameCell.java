public class GameCell {
    private final int x;
    private final int y;
    private GameCellState state;

    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
        state = GameCellState.EMPTY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameCellState getState() {
        return state;
    }

    public void setState(GameCellState state) {
        this.state = state;
    }
}
