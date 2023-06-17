package controller.Kien_Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import controller.Ha_Controller.CourseListController;
import controller.Ha_Controller.GUI11Controller;
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
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Question;
import model2.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private DBInteract dbInteract;
    private String quizName;
    private List<Question> questionList;
    private Map<Integer, List<Integer>> userAnswer;

    public void setQuizName(String quizName) {
        this.quizName = quizName;
        questionList = dbInteract.getQuestionBelongToQuiz(this.quizName);
        addQuestionList();
        renderNavigation();
    }

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
        for (int i = 0; i < questionList.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setQuestion(questionList.get(i));
                questionLayoutController.setAddAnswer();
                if (userAnswer.get(i) != null) {
                    if (userAnswer.get(i).size() > 1) {
                        for (Integer integer : userAnswer.get(i)) {
                            JFXCheckBox selectedCheckBox = (JFXCheckBox) questionLayoutController.questionBox.getChildren().get(integer + 1);
                            selectedCheckBox.setSelected(true);
                            selectedCheckBox.setCheckedColor(Color.GRAY);
                        }
                    } else {
                        JFXRadioButton selectedRadio = (JFXRadioButton) questionLayoutController.questionBox.getChildren().get(userAnswer.get(i).get(0) + 1);
                        selectedRadio.setSelected(true);
                        selectedRadio.setSelectedColor(Color.GRAY);
                    }
                }
                for (int j = 1; j <= questionList.get(i).getOptions().size(); ++j) {
                    Node radioButton = questionLayoutController.questionBox.getChildren().get(j);
                    radioButton.setDisable(true);
                    radioButton.setStyle("-fx-opacity: 1;");
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
                questionRectangleController.setAnswered();
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
            double scrollToY;
            if (i <= 2) scrollToY = quizListContainer.getChildren().get(i).getLayoutY();
            else scrollToY = quizListContainer.getChildren().get(i + 1).getLayoutY();
            if (i > 0.5 * questionList.size()) {
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight() + 1D / questionList.size());
            } else
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight());
        });
    }

    @FXML
    public void finishReview(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Ha_FXML/CourseList.fxml"));
            Node node = fxmlLoader.load();
            CourseListController courseListController = fxmlLoader.getController();
            courseListController.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.removeAddress(5);
            this.headerListener.showMenuButton();
            this.headerListener.showEditingBtn();
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        userAnswer = DataModel.getInstance().getUserAnswer();
    }
}
