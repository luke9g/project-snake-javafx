import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ControllerSavePrompt {
    @FXML public TextField username;
    @FXML public Label scoreInfo;
    @FXML public Label errorText;
    private double finalScore;

    public void initialize() {
        calculateFinalScore();
        scoreInfo.setText("Game over! Your result: " + finalScore);
    }

    private void calculateFinalScore() {
        double d = Board.score * App.timer.getGameLoops() * 10.0 / (GameSettings.rowsCount * GameSettings.colsCount);
        finalScore = Math.round(d * 10) / 10.0;
        App.timer.resetGameLoops();
    }

    public void saveResults() throws IOException {
        String text = username.getText().strip();
        if (!text.matches("[\\d\\p{L} ]{2,20}")) {
            errorText.setText("Please enter 2-20 letters/digits");
            return;
        }
        addNewEntry();
        App.leaderboardEntries.sort(LeaderboardEntry::compareTo);
        goToLeaderboard();
    }

    private void addNewEntry() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
        Calendar c = Calendar.getInstance();
        LeaderboardEntry userEntry = new LeaderboardEntry(username.getText(), sdf.format(c.getTime()), finalScore);
        App.leaderboardEntries.add(userEntry);
        ControllerLeaderboard.newEntry = userEntry;
    }

    public void goToLeaderboard() throws IOException {
        Scene currentScene = scoreInfo.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void goToMainMenu() throws IOException {
        Scene currentScene = username.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void handleKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            saveResults();
        }
    }

    public void exit() {
        Platform.exit();
    }
}
