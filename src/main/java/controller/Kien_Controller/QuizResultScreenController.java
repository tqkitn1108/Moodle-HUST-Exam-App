package controller.Kien_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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

    @FXML
    private HBox userSection;

    VBox[] progressNodes;


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

    Border border = new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 0, 1)));

    public void addQuestionList() {
        for (int i = 0; i < 20; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setAddAnswer();
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void renderNavigation() {
        progressNodes = new VBox[20];
        for (int i = 0; i < 20; i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                questionRectangleController.setAnswered();
                progressNodes[i] = questionRectangleController.getRectangle();
                scrollToQuestion(i);
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scrollToQuestion(int questionIndex) {
        final int i = questionIndex;
        progressNodes[questionIndex].setOnMouseClicked(event -> {
            double scrollToY;
            if (i <= 2) scrollToY = quizListContainer.getChildren().get(i).getLayoutY();
            else scrollToY = quizListContainer.getChildren().get(i + 1).getLayoutY();
            double tmp = (quizListContainer.getHeight() / progressNodes.length) / quizListContainer.getHeight();
            if (i > 0.5 * progressNodes.length) {
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight() + tmp);
            } else
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addQuestionList();
        renderNavigation();
        userSection.setBorder(border);
        progressContent.setBorder(new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 1, 1))));
    }
}
