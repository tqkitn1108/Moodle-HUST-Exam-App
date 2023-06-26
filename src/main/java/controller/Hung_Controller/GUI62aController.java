package controller.Hung_Controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Question;
import model.Quiz;
import model2.DataModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GUI62aController implements Initializable {
    @FXML
    private Label title;
    @FXML
    private MenuButton menuBtn;
    @FXML
    private VBox questionList;
    @FXML
    private Label pencilLabel;
    @FXML
    private Text totalOfMark;
    @FXML
    private JFXCheckBox shuffleBtn;
    @FXML
    private Label quantity;

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private DBInteract dbInteract;
    private List<Question> selectedQuestions;
    private Quiz quiz;

    public void setSelectedQuestions(List<Question> selectedQuestions) {
        this.selectedQuestions = selectedQuestions;
        DataModel.getInstance().setSelectedQuestions(selectedQuestions);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText("Editing quiz: " + quiz.getQuizName());
        shuffleBtn.setSelected(quiz.isShuffle());
    }

    public void addQuestionToScrollPane() {
        quantity.setText(selectedQuestions.size() + "");
        totalOfMark.setText("Total of marks: " + selectedQuestions.size() + ".00");
        DataModel.getInstance().setQuantityLabel(quantity);
        DataModel.getInstance().setTotalMark(totalOfMark);
        pencilLabel.setText("Page 1");
        pencilLabel.setStyle("-fx-text-fill: #c34d53");
        for (int i = 1; i <= selectedQuestions.size(); ++i) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI64.fxml"));
                Node node = fxmlLoader.load();
                QuestionInGUI64Controller questionInGUI64Controller = fxmlLoader.getController();
                questionInGUI64Controller.setOrder(i);
                questionInGUI64Controller.setQuestion(selectedQuestions.get(i - 1));
                questionList.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void openQuestionBank(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI63.fxml"));
            Node node = fxmlLoader.load();
            GUI63Controller gui63Controller = fxmlLoader.getController();
            gui63Controller.setQuiz(quiz);
            gui63Controller.setMainScreen(this.headerListener, this.screenListener);
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addRandomQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI65.fxml"));
            Node node = fxmlLoader.load();
            GUI65Controller gui65Controller = fxmlLoader.getController();
            gui65Controller.setMainScreen(this.headerListener, this.screenListener);
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveEditing(ActionEvent event) {
        try {
            if (selectedQuestions != null) {
                for (Question question : selectedQuestions) {
                    dbInteract.addQuestionToQuiz(quiz.getQuizName(), question.getQuestionName(),null);
                }
            }
            quiz.setShuffle(shuffleBtn.isSelected());
            this.headerListener.removeAddress(1);
            this.screenListener.removeTopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
    }
}
