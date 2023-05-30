package controller.Kien_Controller;

import com.jfoenix.controls.JFXRadioButton;
import controller.Ha_Controller.CourseListController;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listeners.NewScreenListener;
import model2.DataModel;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class QuizResultScreenController implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private Label completedOn;

    @FXML
    private Label finishReviewBtn;

    @FXML
    private Label grade;

    @FXML
    private Label marks;

    @FXML
    private VBox progressContent;

    @FXML
    private FlowPane progressPane;

    @FXML
    private VBox quizListContainer;

    public Label startedOn;

    @FXML
    private Label state;

    @FXML
    private Label time;

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    private Integer questionQuantity;
    private Map<Integer, Integer> userAnswer;

    public void setStartedOn(String text) {
        this.startedOn.setText(text);
    }

    public void setCompletedOn(String text) {
        this.completedOn.setText(text);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setMarks(String marks) {
        this.marks.setText(marks);
    }

    public void setGrade(String grade) {
        this.grade.setText(grade);
    }

    public void addQuestionList() {
        for (int i = 0; i < questionQuantity; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setAddAnswer();
                if (userAnswer.get(i) != null) {
                    RadioButton selectedRadio = (RadioButton) questionLayoutController.questionBox.getChildren().get(userAnswer.get(i));
                    selectedRadio.setSelected(true);
                }
                for (int j= 1;j<=4;++j) {
                    JFXRadioButton radioButton = (JFXRadioButton) questionLayoutController.questionBox.getChildren().get(j);
                    radioButton.setDisable(true);
                    radioButton.setStyle("-fx-opacity: 1;");
                    radioButton.setSelectedColor(Color.GRAY);
                }
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void renderNavigation() {
        for (int i = 0; i < questionQuantity; i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                questionRectangleController.setAnswered();
                scrollToQuestion(i,questionRectangleController);
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scrollToQuestion(int questionIndex, QuestionRectangleController questionRectangleController) {
        final int i = questionIndex;
        questionRectangleController.getRectangle().setOnMouseClicked(event -> {
            double scrollToY;
            if (i <= 2) scrollToY = quizListContainer.getChildren().get(i).getLayoutY();
            else scrollToY = quizListContainer.getChildren().get(i + 1).getLayoutY();
            if (i > 0.5 * questionQuantity) {
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight() + 1D / questionQuantity);
            } else
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight());
        });
    }

    @FXML
    public void finishReview(MouseEvent event) {
        try {
            Stage stage = new Stage();
            stage.setMaximized(true);
            Parent root = FXMLLoader.load(getClass().getResource("/Ha_FXML/GUI11.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Hệ thống ôn thi trắc nghiệm");
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) finishReviewBtn.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionQuantity = DataModel.getInstance().getNumber();
        userAnswer = DataModel.getInstance().getUserAnswer();
        addQuestionList();
        renderNavigation();
    }
}
