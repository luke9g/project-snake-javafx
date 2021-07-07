import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends Application {
    public static Timer timer = new Timer();
    public static ArrayList<LeaderboardEntry> leaderboardEntries;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        primaryStage.setTitle("Snake");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.show();

        leaderboardEntries = loadLeaderboardEntries();
        timer.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.interrupt();
        saveLeaderboardEntries();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<LeaderboardEntry> loadLeaderboardEntries() {
        ArrayList<LeaderboardEntry> entries = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileReader("leaderboard.txt", StandardCharsets.UTF_8));

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] words = line.split("\t");
                try {
                    double score = Double.parseDouble(words[2]);
                    entries.add(new LeaderboardEntry(words[0], words[1], score));
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Incorrect entry format: " + line);
                    System.err.println("The entry will be deleted.");
                } catch (NumberFormatException e) {
                    System.err.println("Incorrect score format: " + "'" + words[2] + "'");
                    System.err.println("Changed the score to 0.");
                    entries.add(new LeaderboardEntry(words[0], words[1], 0));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Path.of("leaderboard.txt"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    private void saveLeaderboardEntries() {
        try {
            PrintStream ps = new PrintStream("leaderboard.txt", StandardCharsets.UTF_8);
            leaderboardEntries.forEach(ps::println);
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
