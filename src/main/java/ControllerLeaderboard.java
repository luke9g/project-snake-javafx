import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLeaderboard {

    @FXML public VBox nameColumn;
    @FXML public VBox dateColumn;
    @FXML public VBox scoreColumn;
    @FXML public ScrollPane scrollPane;
    private VBox[] columns;

    public static LeaderboardEntry newEntry;

    public void initialize() {
        columns = new VBox[]{nameColumn, dateColumn, scoreColumn};
        App.leaderboardEntries.forEach(this::writeEntry);
        addSpecialClassesToRows();
    }

    private void addSpecialClassesToRows() {
        for (VBox column : columns) {
            try {
                column.getChildren().get(0).getStyleClass().add("row-first");
                column.getChildren().get(1).getStyleClass().add("row-second");
                column.getChildren().get(2).getStyleClass().add("row-third");
                column.getChildren().get(column.getChildren().size() - 1).getStyleClass().add("row-last");
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }

    private void writeEntry(LeaderboardEntry entry) {
        boolean isNewEntry = (entry == newEntry);
        writeCell(entry.getName(), nameColumn, isNewEntry);
        writeCell(entry.getDate(), dateColumn, isNewEntry);
        HBox cell = writeCell(String.valueOf(entry.getScore()), scoreColumn, isNewEntry);
        if (isNewEntry) Platform.runLater(() -> autoScrollToCell(cell));
    }

    private HBox writeCell(String text, VBox column, boolean isHighlighted) {
        Label label = new Label(text);
        label.getStyleClass().add("entry-text");

        HBox cell = new HBox();
        cell.getChildren().add(label);
        cell.getStyleClass().add("row");
        if (isHighlighted) cell.getStyleClass().add("row-highlighted");

        column.getChildren().add(cell);
        cell.setAlignment(Pos.CENTER);

        return cell;
    }

    private void autoScrollToCell(HBox cell) {
        double cellY = cell.getParent().getBoundsInLocal().getMaxY();
        double scrollPaneTotalHeight = cell.getBoundsInParent().getMaxY();
        double scrollPaneVisibleHeight = scrollPane.getViewportBounds().getMaxY() / cellY;
        double cellYRatio = scrollPaneTotalHeight / cellY;
        double scrollValue;
        if (cellYRatio < 0.5) {
            scrollValue = cellYRatio - 0.25 * scrollPaneVisibleHeight;
            if (scrollValue < 0) {
                scrollValue = 0;
            }
        } else {
            scrollValue = cellYRatio + 0.25 * scrollPaneVisibleHeight;
            if (scrollValue > 1) {
                scrollValue = 1;
            }
        }
        scrollPane.setVvalue(scrollValue);
    }

    public void goToMainMenu() throws IOException {
        Scene currentScene = nameColumn.getScene();
        Stage currentWindow = (Stage) currentScene.getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene newScene = new Scene(root, 600, 600);

        currentWindow.setScene(newScene);
    }

    public void exit() {
        Platform.exit();
    }
}
