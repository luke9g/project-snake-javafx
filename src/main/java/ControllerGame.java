import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerGame {

    @FXML private Canvas canvas;
    private Board board;
    private GraphicsContext context;
    private boolean buttonPressed;
    private AnimationTimer animationTimer;

    public void initialize() {
        board = new Board(GameSettings.rowsCount, GameSettings.colsCount);
        canvas.setWidth(GameSettings.colsCount * GameSettings.cellSize - GameSettings.gridLineThickness);
        canvas.setHeight(GameSettings.rowsCount * GameSettings.cellSize - GameSettings.gridLineThickness);
        context = canvas.getGraphicsContext2D();

        context.setFill(Color.web("003D00", 0.5));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (GameCell[] gameCellRows : board.getGameCells()) {
            for (GameCell gameCell : gameCellRows) {
                paintCell(gameCell);
            }
        }

        startGameLoop();
    }

    private void startGameLoop() {
        App.timer.unfreeze();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (App.timer.canUpdate()) {
                    App.timer.setCanUpdate(false);
                    if (!update()) {
                        stop();
                        App.timer.freeze();
                    }
                }
            }
        };
        animationTimer.start();
    }

    private void paintCell(GameCell gameCell) {
        if (gameCell.getState() == GameCellState.EMPTY) {
            context.setFill(Colors.empty);
        } else if (gameCell.getState() == GameCellState.SNAKE_HEAD) {
            context.setFill(Colors.snakeHead);
        } else if (gameCell.getState() == GameCellState.SNAKE_BODY) {
            context.setFill(Colors.snakeBody);
        } else if (gameCell.getState() == GameCellState.FOOD_NORMAL) {
            context.setFill(Colors.normalFood);
        } else if (gameCell.getState() == GameCellState.FOOD_SPECIAL) {
            context.setFill(Colors.specialFood);
        }
        context.fillRect(gameCell.getY() * GameSettings.cellSize, gameCell.getX() * GameSettings.cellSize,
                GameSettings.cellSizeInGrid, GameSettings.cellSizeInGrid);
    }

    private boolean update() {
        ArrayList<GameCell> updatedCells = board.update();
        if (updatedCells.size() == 0) {
            try {
                Thread.sleep(750);
                goToSavePrompt();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        updatedCells.forEach(this::paintCell);
        buttonPressed = false;
        return true;
    }

    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        if (buttonPressed) return;
        if (keyEvent.getCode() == KeyCode.UP && board.getMovingDirection() != MovementDirection.DOWN) {
            buttonPressed = true;
            board.setMovingDirection(MovementDirection.UP);
        } else if (keyEvent.getCode() == KeyCode.DOWN && board.getMovingDirection() != MovementDirection.UP) {
            buttonPressed = true;
            board.setMovingDirection(MovementDirection.DOWN);
        } else if (keyEvent.getCode() == KeyCode.RIGHT && board.getMovingDirection() != MovementDirection.LEFT) {
            buttonPressed = true;
            board.setMovingDirection(MovementDirection.RIGHT);
        } else if (keyEvent.getCode() == KeyCode.LEFT && board.getMovingDirection() != MovementDirection.RIGHT) {
            buttonPressed = true;
            board.setMovingDirection(MovementDirection.LEFT);
        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            if (App.timer.isFrozen()) {
                App.timer.unfreeze();
            } else {
                App.timer.freeze();
            }
        }
    }

    public void initKeyShortcutListener() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        Runnable action = () -> {
            try {
                goToMainMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        canvas.getScene().getAccelerators().put(keyCombination, action);
    }

    private void goToMainMenu() throws IOException {
        animationTimer.stop();
        App.timer.freeze();

        Scene currentScene = canvas.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    private void goToSavePrompt() throws IOException {
        animationTimer.stop();
        App.timer.freeze();

        Scene currentScene = canvas.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("SavePrompt.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }
}
