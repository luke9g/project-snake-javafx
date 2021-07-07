import java.util.ArrayList;

public class Board {
    private final int rows;
    private final int cols;
    private GameCell[][] gameCells;
    private ArrayList<GameCell> snakeCells;
    private boolean isSpecialFood;
    private MovementDirection movingDirection = MovementDirection.UP;

    public static int score;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initGameCells();
        initSnakeCells();
        generateNormalFoodCell();
        generateSpecialFoodCell();
        score = 0;
    }

    private void initGameCells() {
        gameCells = new GameCell[rows][cols];
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[i].length; j++) {
                gameCells[i][j] = new GameCell(i, j);
            }
        }
    }

    private void initSnakeCells() {
        snakeCells = new ArrayList<>();
        for (int i = rows / 2; i < rows / 2 + 1; i++) {
            GameCell snakeCell = gameCells[i][cols / 2];
            snakeCell.setState(GameCellState.SNAKE_BODY);
            if (i == rows / 2) snakeCell.setState(GameCellState.SNAKE_HEAD);
            snakeCells.add(snakeCell);
        }
    }

    public GameCell getRandomEmptyCell() {
        GameCell cell;
        do {
            cell = gameCells[(int) (Math.random() * rows)][(int) (Math.random() * cols)];
        } while (cell.getState() != GameCellState.EMPTY);
        return cell;
    }

    public GameCell generateNormalFoodCell() {
        GameCell newNormal = getRandomEmptyCell();
        newNormal.setState(GameCellState.FOOD_NORMAL);
        return newNormal;
    }

    public GameCell generateSpecialFoodCell() {
        GameCell newSpecial = getRandomEmptyCell();
        newSpecial.setState(GameCellState.FOOD_SPECIAL);
        isSpecialFood = true;
        return newSpecial;
    }

    public GameCell[][] getGameCells() {
        return gameCells;
    }

    public void setMovingDirection(MovementDirection movingDirection) {
        this.movingDirection = movingDirection;
    }

    public MovementDirection getMovingDirection() {
        return movingDirection;
    }

    public ArrayList<GameCell> update() {
        ArrayList<GameCell> updatedCells = new ArrayList<>();

        GameCell head = snakeCells.get(0);
        GameCell newHead;
        try {
            switch (movingDirection) {
                case UP: newHead = gameCells[head.getX() - 1][head.getY()]; break;
                case DOWN: newHead = gameCells[head.getX() + 1][head.getY()]; break;
                case RIGHT: newHead = gameCells[head.getX()][head.getY() + 1]; break;
                case LEFT: newHead = gameCells[head.getX()][head.getY() - 1]; break;
                default: newHead = gameCells[head.getX()][head.getY()];
            }
            if (newHead.getState() == GameCellState.SNAKE_BODY) {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException ex) {
            return updatedCells;
        }

        GameCell snakeEnd;
        if (newHead.getState() == GameCellState.FOOD_NORMAL) {
            score++;
            updatedCells.add(generateNormalFoodCell());
            if (Math.random() <= 0.1 && !isSpecialFood) {
                updatedCells.add(generateSpecialFoodCell());
            }
        } else if (newHead.getState() == GameCellState.FOOD_SPECIAL) {
            score++;
            isSpecialFood = false;
            snakeEnd = removeSnakeEnd();
            if (snakeEnd != null) updatedCells.add(snakeEnd);
            snakeEnd = removeSnakeEnd();
            if (snakeEnd != null) updatedCells.add(snakeEnd);
        } else {
            snakeEnd = removeSnakeEnd();
            if (snakeEnd != null) updatedCells.add(snakeEnd);
        }

        GameCell oldHead = addSnakeHead(newHead);
        if (oldHead != null) updatedCells.add(oldHead);
        updatedCells.add(newHead);

        return updatedCells;
    }

    public GameCell removeSnakeEnd() {
        if (snakeCells.size() == 0) {
            return null;
        }
        GameCell end = snakeCells.get(snakeCells.size() - 1);
        snakeCells.remove(end);
        end.setState(GameCellState.EMPTY);
        return end;
    }

    public GameCell addSnakeHead(GameCell newHead) {
        snakeCells.add(0, newHead);
        newHead.setState(GameCellState.SNAKE_HEAD);
        if (snakeCells.size() == 1) {
            return null;
        }
        GameCell oldHead = snakeCells.get(1);
        oldHead.setState(GameCellState.SNAKE_BODY);
        return oldHead;
    }
}
