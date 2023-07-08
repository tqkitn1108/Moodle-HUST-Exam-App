package controller.Hung_Controller;

import com.jfoenix.controls.JFXCheckBox;
import controller.Ha_Controller.CourseListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Question;
import model.Quiz;
import model.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GUI62Controller implements Initializable {
    @FXML
    private Label title;
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
    @FXML
    private ComboBox<String> jumpBox;

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
        if (selectedQuestions != null) {
            ObservableList<Question> list = FXCollections.observableArrayList();
            list.addAll(selectedQuestions);
            this.selectedQuestions.addAll(list);
        }
        DataModel.getInstance().setSelectedQuestions(this.selectedQuestions);
        addQuestionToScrollPane();
    }

    public void addQuestionToScrollPane() {
        quantity.setText(String.valueOf(selectedQuestions.size()));
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
    public void openQuestionBank(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI63.fxml"));
        Node node = fxmlLoader.load();
        GUI63Controller gui63Controller = fxmlLoader.getController();
        gui63Controller.setMainScreen(this.headerListener, this.screenListener);
        this.screenListener.changeScreen(node);
    }

    @FXML
    public void addRandomQuestion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI65.fxml"));
        Node node = fxmlLoader.load();
        GUI65Controller gui65Controller = fxmlLoader.getController();
        gui65Controller.setMainScreen(this.headerListener, this.screenListener);
        this.screenListener.changeScreen(node);
    }

    @FXML
    public void saveEditing(ActionEvent event) throws Exception {
        for (Question question : quiz.getQuestions()) {
            dbInteract.removeQuestionFromQuiz(quiz.getQuizName(), question.getQuestionName());
        }
        if (selectedQuestions != null) {
            for (Question question : selectedQuestions) {
                dbInteract.addQuestionToQuiz(quiz.getQuizName(), question.getQuestionName(), null);
            }
        }
        quiz.setShuffle(shuffleBtn.isSelected());
        dbInteract.setShuffle(quiz.getQuizName(), shuffleBtn.isSelected());
        this.headerListener.removeAddress(1);
        this.screenListener.removeTopScreen();
    }

    @FXML
    public void jumpToAnotherPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
        Node node = fxmlLoader.load();
        CourseListController courseListController = fxmlLoader.getController();
        courseListController.setMainScreen(this.headerListener, this.screenListener);
        this.headerListener.showMenuButton();
        this.headerListener.showEditingBtn();
        this.headerListener.removeAddress(5);
        this.screenListener.removeTopScreen();
        this.screenListener.changeScreen(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        quiz = DataModel.getInstance().getQuiz();
        title.setText("Editing quiz: " + quiz.getQuizName());
        shuffleBtn.setSelected(quiz.isShuffle());
        jumpBox.getItems().add("Home");
        if (DataModel.getInstance().getSelectedQuestions() == null) {
            selectedQuestions = quiz.getQuestions();
        } else selectedQuestions = DataModel.getInstance().getSelectedQuestions();
    }
}
