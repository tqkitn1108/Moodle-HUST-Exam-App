package controller.Kien_Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import controller.Ha_Controller.CourseListController;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Question;
import model2.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private Text gradeText;

    @FXML
    private Label marks;

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
    private List<Question> questionList;

    private List<Node> questionNodes;
    private List<QuestionLayoutController> questionLayoutControllers;
    private Map<Integer, List<Integer>> userAnswer;
    private Map<Integer, List<Integer>> correctAnswers;

    public void setQuizName(String quizName) {
        questionList = dbInteract.getQuestionBelongToQuiz(quizName);
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

    public void setGrade(String gradeText, String grade) {
        this.gradeText.setText(gradeText);
        this.grade.setText(grade);
    }

    public void addQuestionList() {
        for (int i = 0; i < questionNodes.size(); ++i) {
            questionLayoutControllers.get(i).setAddAnswer(questionList.get(i));
            correctAnswers.put(i, questionLayoutControllers.get(i).getCorrectAnswerList());
            if (!userAnswer.get(i).equals(List.of(-1))) {
                for (Integer integer : userAnswer.get(i)) {
                    Node answerNode = questionLayoutControllers.get(i).questionBox.getChildren().get(integer + 2);
                    if (answerNode instanceof CheckBox selectedCheckBox) {
                        selectedCheckBox.getStyleClass().add("selected-choice");
                    } else {
                        JFXRadioButton selectedRadio = (JFXRadioButton) answerNode;
                        selectedRadio.setSelectedColor(Color.GRAY);
                    }
                }
            }
            if (userAnswer.get(i).equals(correctAnswers.get(i))) {
                questionLayoutControllers.get(i).setStateQues("Correct Answer");
            } else questionLayoutControllers.get(i).setStateQues("Wrong Answer");
            for (int j = 1; j <= questionList.get(i).getChoices().size(); ++j) {
                Node option = questionLayoutControllers.get(i).questionBox.getChildren().get(j+1);
                option.setDisable(true);
                option.setStyle("-fx-opacity: 1;");
            }
            quizListContainer.getChildren().add(questionNodes.get(i));
        }
    }

    public void renderNavigation() {
        for (int i = 0; i < questionList.size(); i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                if (userAnswer.get(i).equals(correctAnswers.get(i))) {
                    questionRectangleController.setRightAnswered();
                } else questionRectangleController.setWrongAnswered();
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
        correctAnswers = new HashMap<>();
        questionNodes = DataModel.getInstance().getQuestionNodes();
        questionLayoutControllers = DataModel.getInstance().getQuestionLayoutControllers();
    }
}
