import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerSettingsMenu {

    @FXML Slider rowsSlider;
    @FXML Slider colsSlider;
    @FXML CheckBox showGridLines;

    public void goToGame() throws IOException {
        initializeSettings();

        Scene currentScene = rowsSlider.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = loader.load();

        Scene newScene = new Scene(root,
                GameSettings.cellSize * GameSettings.colsCount - GameSettings.gridLineThickness,
                GameSettings.cellSize * GameSettings.rowsCount - GameSettings.gridLineThickness
        );

        currentWindow.setScene(newScene);
        root.requestFocus();

        ((ControllerGame) loader.getController()).initKeyShortcutListener();
    }

    private void initializeSettings() {
        GameSettings.rowsCount = (int) rowsSlider.getValue();
        GameSettings.colsCount = (int) colsSlider.getValue();
        if (showGridLines.isSelected()) {
            GameSettings.gridLineThickness = 1;
            GameSettings.cellSizeInGrid = GameSettings.cellSize - GameSettings.gridLineThickness;
        }
    }

    public void goToMainMenu() throws IOException {
        Scene currentScene = rowsSlider.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void handleKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            goToGame();
        }
    }

    public void exit() {
        Platform.exit();
    }
}
