package start;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TEST1 extends Application {
    private LocalDateTime startTime;
    private Text durationText;

    @Override
    public void start(Stage stage) {
        Text startTimeText = new Text();
        durationText = new Text();
        Button startButton = new Button("Start");
        Button endButton = new Button("End");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy, h:mm:ss a", Locale.ENGLISH);

        startButton.setOnAction(event -> {
            startTime = LocalDateTime.now();
            String formattedStartTime = startTime.format(formatter);
            startTimeText.setText("Start time: " + formattedStartTime);
            durationText.setText("");
        });

        endButton.setOnAction(event -> {
            if (startTime != null) {
                LocalDateTime endTime = LocalDateTime.now();
                String formattedEndTime = endTime.format(formatter);
                String formattedStartTime = startTime.format(formatter);
                long seconds = Duration.between(startTime, endTime).getSeconds();
                String duration = formatDuration(seconds);
                String durationMessage = "Duration: " + duration;

                startTimeText.setText("Start time: " + formattedStartTime);
                durationText.setText("End time: " + formattedEndTime + "\n" + durationMessage);
            } else {
                durationText.setText("Please click the Start button first");
            }
        });

        VBox root = new VBox(startTimeText, durationText, startButton, endButton);
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public static void main(String[] args) {
        launch();
    }
}