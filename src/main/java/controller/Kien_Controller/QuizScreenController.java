package controller.Kien_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class QuizScreenController implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label time;
    @FXML
    private HBox userSection;
    @FXML
    private VBox progressContent;
    @FXML
    private FlowPane progressPane;
    @FXML
    private VBox quizListContainer;

    Border border = new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 0, 1)));

    // Timer fields
    private long hr, min, sec, totalSec = 0;

    private String format(long value) {
        if (value < 10) {
            return 0 + "" + value;
        }
        return value + "";
    }

    public void convertTime() {
        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - min * 60;
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - hr * 60;
        time.setText("Time left " + hr + ":" + format(min) + ":" + format(sec));
        totalSec--;
    }

    private void setTimer() {
        totalSec = 300;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        convertTime();
                        if (totalSec <= 0) {
                            timer.cancel();
                            time.setText("Time left 00:00:00");
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void addQuestionList() {
        for (int i = 0; i < 20; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i+1);
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addQuestionList();
        for (int i = 0; i < 20; i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                questionRectangleController.setDefault();
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setTimer();
        userSection.setBorder(border);
        progressContent.setBorder(new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 1, 1))));
    }
}
