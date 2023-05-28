package controller.Kien_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.NewScreenListener;
import model2.DataModel;
import model2.Question;
import model2.Quiz;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuizScreenController implements Initializable {
    @FXML
    private Label finishBtn;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private Label time;
    @FXML
    private VBox progressContent;
    @FXML
    private FlowPane progressPane;
    @FXML
    private VBox quizListContainer;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Quiz quiz;
    private List<Question> questionList;
    private Map<Integer, Integer> userAnswer;
    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    private Timer timer = new Timer();

    ToggleGroup[] toggleGroups;
    Pane overlay;
    StackPane overlayStackPane;
    Stage confirmStage;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.getData();
    }

    private void getData() {
        if (quiz != null) {
            this.questionList = quiz.getQuestions();
        }
    }

    // Xử lý khi hover vào finish Attempt
    @FXML
    void hoverFinishBtn(MouseEvent event) {
        finishBtn.setUnderline(true);
    }

    @FXML
    void unhoverFinishBtn(MouseEvent event) {
        finishBtn.setUnderline(false);
    }

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
        totalSec = questionList.size() * 30L;
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        convertTime();
                        if (totalSec <= 0) {
                            time.setText("Time left 00:00:00");
                            changeScreen();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void addQuestionList() {
        startTime = LocalDateTime.now();
        // Tạo ra questionList mẫu làm ví dụ
        questionList = new ArrayList<Question>();
        for (int i = 0; i < 200; i++) {
            questionList.add(new Question("Question " + (i + 1)));
        }

        toggleGroups = new ToggleGroup[questionList.size()];
        for (int i = 0; i < questionList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setQuestionContent(questionList.get(i).getQuestion());
                toggleGroups[i] = questionLayoutController.choice;
                int finalI = i;
                toggleGroups[i].selectedToggleProperty().addListener(
                        (ov, oldToggle, newToggle) -> {
                            if (newToggle != null) {
                                questionLayoutController.setStateQues("Answered");
                                for (int j = 1; j <= 4; ++j) {
                                    RadioButton selectedRadio = (RadioButton) questionLayoutController.questionBox.getChildren().get(j);
                                    if (selectedRadio.isSelected()) {
                                        userAnswer.put(finalI, j);
                                        break;
                                    }
                                }
                            }
                        }
                );
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void renderNavigation() {
        for (int i = 0; i < questionList.size(); i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                toggleGroups[i].selectedToggleProperty().addListener(
                        (ov, oldToggle, newToggle) -> {
                            if (newToggle != null) {
                                questionRectangleController.setAnswered();

                            } else {
                                questionRectangleController.setDefault();
                            }
                        }
                );
                scrollToQuestion(i, questionRectangleController);
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scrollToQuestion(int questionIndex, QuestionRectangleController questionRectangleController) {
        final int i = questionIndex;
        questionRectangleController.getRectangle().setOnMouseClicked(event -> {
            double scrollToY = quizListContainer.getChildren().get(i).getLayoutY();
            double tmp = 1D / questionList.size();// tmp là tỉ lệ % chiều cao của một câu hỏi trong quizListContainer
            if (i > 0.5 * questionList.size()) {
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight() + tmp);
            } else
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight());
        });
    }

    public void finishAttempt(MouseEvent event) throws IOException {
        // Cài đặt cho primaryStage khi hiện cửa sổ xác nhận
        Stage thisStage = (Stage) finishBtn.getScene().getWindow(); // Lấy Stage hiện tại
        Scene thisScene = thisStage.getScene();
        overlayStackPane = new StackPane(thisScene.getRoot()); // Tạo một StackPane để chứa Stage và Overlay
        overlayStackPane.setPrefSize(thisStage.getWidth() - 15, thisStage.getHeight() - 38);
        overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-opacity: 1;");
        overlayStackPane.getChildren().add(overlay);
        Scene newScene = new Scene(overlayStackPane);
        thisStage.setScene(newScene); // Đặt Scene mới cho thisStage

        // Cài đặt cho confirm window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Kien_FXML/ConfirmSubmit.fxml"));
        Parent confirmPane = loader.load();
        Scene scene = new Scene(confirmPane);
        confirmStage = new Stage();
        confirmStage.initStyle(StageStyle.UNDECORATED); // Bỏ thanh bar mặc định ở trên cửa sổ
        // Ngăn user tương tác được với primaryStage khi hiện cửa sổ, có thể dùng APPLICATION_MODAL thay cho WINDOW_MODAL
        confirmStage.initModality(Modality.WINDOW_MODAL);
        confirmStage.initOwner(thisStage);

        // set scene cho confirmStage và chờ đợi event
        confirmStage.setScene(scene);
        confirmStage.showAndWait();

        ConfirmSubmitController confirmSubmitController = loader.getController();
        if (confirmSubmitController.isCancelled) {
            overlayStackPane.getChildren().remove(overlay);
        }
        if (confirmSubmitController.isSubmitted) {
            changeScreen();
        }
    }

    private void changeScreen() {
        finishTime = LocalDateTime.now();
        timer.cancel();
        if (confirmStage != null) {
            confirmStage.close();
            overlayStackPane.getChildren().remove(overlay);
        }
        DataModel.getInstance().setNumber(questionList.size());
        DataModel.getInstance().setUserAnswer(userAnswer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizResultScreen.fxml"));
        try {
            Node node = fxmlLoader.load();
            QuizResultScreenController quizResultScreenController = fxmlLoader.getController();
            setResultBar(quizResultScreenController);
            quizResultScreenController.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResultBar(QuizResultScreenController quizResultScreenController) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy, h:mm a", Locale.ENGLISH);
        long seconds = Duration.between(startTime, finishTime).getSeconds();
        quizResultScreenController.setTime(formatDuration(seconds));
        quizResultScreenController.setStartedOn(startTime.format(formatter));
        quizResultScreenController.setCompletedOn(finishTime.format(formatter));
        quizResultScreenController.setMarks("/" + questionList.size() + ".00");
        quizResultScreenController.setGrade(0.00 + " out of 10.00 (" + 0 + "%)");
    }

    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        String hrText = "", minText = "", secText = "";
        if (hours == 1) hrText = "1 hour ";
        else if (hours > 1) hrText = hours + " hours ";
        if (minutes == 1) minText = "1 min ";
        else if (minutes > 1) minText = minutes + " mins ";
        if (remainingSeconds == 1) secText = "1 sec";
        else if (remainingSeconds > 1) secText = remainingSeconds + " secs ";
        return hrText + minText + secText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userAnswer = new HashMap<>(); // userAnswer map một cặp (i,j) với j là đáp án của câu hỏi thứ i
        addQuestionList();
        renderNavigation();
        setTimer();
    }
}
