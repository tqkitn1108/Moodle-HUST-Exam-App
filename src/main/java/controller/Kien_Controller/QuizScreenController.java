package controller.Kien_Controller;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.jfoenix.controls.JFXCheckBox;
import controller.Ha_Controller.CourseListController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Question;
import model.Quiz;
import model2.DataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizScreenController implements Initializable {
    @FXML
    private Label finishBtn;
    @FXML
    private MFXButton exportBtn;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private Label time;
    @FXML
    private FlowPane progressPane;
    @FXML
    private VBox quizListContainer;

    private DBInteract dbInteract;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Quiz quiz;
    private List<Question> questionList;
    private Map<Integer, List<Integer>> userAnswer;
    private Map<Integer, List<Integer>> correctAnswers;
    private Integer numberOfRightAnswers;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    public void hideTimer() {
        this.time.setVisible(false);
    }

    private Timer timer;

    ToggleGroup[] toggleGroups;
    Pane overlay;
    StackPane overlayStackPane;
    Stage confirmStage;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        questionList = quiz.getQuestions();
        addQuestionList();
        renderNavigation();
        setTimer();
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
        totalSec = quiz.getTimeLimit() * 60L;
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
        toggleGroups = new ToggleGroup[questionList.size()];
        for (int i = 0; i < questionList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setQuestion(questionList.get(i));
                correctAnswers.put(i, questionLayoutController.getCorrectAnswerList());
                userAnswer.put(i, List.of(-1));
                toggleGroups[i] = questionLayoutController.getChoiceGroup();
                int size = questionList.get(i).getOptions().size();
                if (questionList.get(i).isMultipleAnswer()) {
                    Set<JFXCheckBox> checkBoxes = questionLayoutController.getCheckBoxGroup();
                    AtomicInteger count = new AtomicInteger(); // Vai trò như biến đếm count (count đếm số lượng checkbox được select)
                    for (JFXCheckBox checkBox : checkBoxes) {
                        int finalI1 = i;
                        checkBox.selectedProperty().addListener(
                                (observable, oldValue, newValue) -> {
                                    if (newValue) {
                                        count.getAndIncrement();
                                        questionLayoutController.setStateQues("Answered");
                                        RadioButton radioButton = new RadioButton();
                                        radioButton.setSelected(true);
                                        radioButton.setToggleGroup(toggleGroups[finalI1]);
                                        List<Integer> answerList = new ArrayList<Integer>();
                                        for (int j = 1; j <= size; ++j) {
                                            JFXCheckBox selectedCheckBox = (JFXCheckBox) questionLayoutController.questionBox.getChildren().get(j);
                                            if (selectedCheckBox.isSelected()) {
                                                answerList.add(j - 1);
                                            }
                                        }
                                        userAnswer.put(finalI1, answerList);
                                    } else {
                                        count.getAndDecrement();
                                        if (count.get() == 0) {
                                            questionLayoutController.setStateQues("Not yet answered");
                                            RadioButton radioButton = new RadioButton();
                                            radioButton.setSelected(true);
                                            radioButton.setToggleGroup(toggleGroups[finalI1]);
                                            radioButton.setSelected(false); //chuyển selected từ true -> false để thực hiện questionRectangleController.setDefault();
                                            userAnswer.put(finalI1, List.of(-1));
                                        } else {
                                            List<Integer> answerList = new ArrayList<Integer>();
                                            for (int j = 1; j <= size; ++j) {
                                                JFXCheckBox selectedCheckBox = (JFXCheckBox) questionLayoutController.questionBox.getChildren().get(j);
                                                if (selectedCheckBox.isSelected()) {
                                                    answerList.add(j - 1);
                                                }
                                            }
                                            userAnswer.put(finalI1, answerList);
                                        }
                                    }
                                }
                        );
                    }
                } else {
                    int finalI = i;
                    toggleGroups[i].selectedToggleProperty().addListener(
                            (ov, oldToggle, newToggle) -> {
                                if (newToggle != null) {
                                    questionLayoutController.setStateQues("Answered");
                                    List<Integer> answerList = new ArrayList<Integer>();
                                    for (int j = 1; j <= size; ++j) {
                                        RadioButton selectedRadio = (RadioButton) questionLayoutController.questionBox.getChildren().get(j);
                                        if (selectedRadio.isSelected()) {
                                            answerList.add(j - 1);
                                            userAnswer.put(finalI, answerList);
                                            break;
                                        }
                                    }
                                }
                            }
                    );
                }
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
        thisScene.setRoot(overlayStackPane);
        thisStage.setScene(thisScene);

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
        for (int i = 0; i < questionList.size(); i++) {
            if (userAnswer.get(i).equals(correctAnswers.get(i))) {
                numberOfRightAnswers++;
            }
        }
        DataModel.getInstance().setNumber(questionList.size());
        DataModel.getInstance().setUserAnswer(userAnswer);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizResultScreen.fxml"));
            Node node = fxmlLoader.load();
            QuizResultScreenController quizResultScreenController = fxmlLoader.getController();
            quizResultScreenController.setQuizName(quiz.getQuizName());
            setResultBar(quizResultScreenController);
            quizResultScreenController.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.removeAddress(1);
            this.headerListener.addAddressToBreadcrumbs("Review");
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
        quizResultScreenController.setMarks(numberOfRightAnswers + ".00" + "/" + questionList.size() + ".00");
        String gradeText = String.format("%.2f", 10D * numberOfRightAnswers / questionList.size()).replace(",", ".");
        String gradePercent = String.format("%.0f", 100D * numberOfRightAnswers / questionList.size());
        quizResultScreenController.setGrade(gradeText, "out of 10.00 (" + gradePercent + "%)");
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

    @FXML
    public void exportToPdf() throws Exception {
        try {
            finishBtn.setVisible(false);
            exportBtn.setVisible(true);
            Stage thisStage = (Stage) exportBtn.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("Baithitracnghiem.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(thisStage);

            if (file != null) {
                PdfWriter writer = new PdfWriter(new FileOutputStream(file.getPath()));
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument, PageSize.A4);
                document.setMargins(50, 50, 50, 50);

                double vboxHeight = quizListContainer.getHeight();
                double partWidth = quizListContainer.getWidth();
                double startY = 0; // Vị trí bắt đầu chụp của VBox ở mỗi lần chụp
                while (vboxHeight > startY) {
                    double partHeight = vboxHeight - startY; // Phần VBox còn cần phải chụp

                    // Tạo một đối tượng Rectangle để chỉ định phần của VBox bạn muốn chụp
                    Rectangle2D partRect = new Rectangle2D(0, startY, partWidth, partHeight);

                    SnapshotParameters params = new SnapshotParameters();
                    params.setViewport(partRect);

                    // Chuyển phần của VBox sang hình ảnh và chụp vào tài liệu PDF
                    WritableImage partImage = quizListContainer.snapshot(params, null);
                    Image partPdfImage = new Image(ImageDataFactory.create(SwingFXUtils.fromFXImage(partImage, null), null));
                    document.add(partPdfImage);
                    startY += 1700;
                }
                document.close();

                // Sau khi tạo file PDF thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText("Tài liệu PDF đã được tạo thành công");
                alert.setContentText("Thông báo thành công.");

                alert.setOnCloseRequest(event -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
                        Node node = fxmlLoader.load();
                        CourseListController courseListController = fxmlLoader.getController();
                        courseListController.setMainScreen(this.headerListener, this.screenListener);
                        this.headerListener.showMenuButton();
                        this.headerListener.showEditingBtn();
                        this.headerListener.removeAddress(5);
                        this.screenListener.removeTopScreen();
                        this.screenListener.changeScreen(node);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                alert.showAndWait();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        userAnswer = new HashMap<>(); // userAnswer map một cặp (i,j) với j là đáp án của câu hỏi thứ i, i>=0
        correctAnswers = new HashMap<>();
        numberOfRightAnswers = 0;
    }
}
