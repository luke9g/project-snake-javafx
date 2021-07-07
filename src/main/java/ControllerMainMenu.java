import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMainMenu {

    @FXML public VBox vbox;

    public void goToSettingsMenu() throws IOException {
        Scene currentScene = vbox.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void goToLeaderboard() throws IOException {
        Scene currentScene = vbox.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void exit() {
        Platform.exit();
    }
}
