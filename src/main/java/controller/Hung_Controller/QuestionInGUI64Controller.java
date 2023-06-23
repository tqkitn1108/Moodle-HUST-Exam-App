package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Question;
import model2.DataModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class QuestionInGUI64Controller implements Initializable {

    @FXML
    private Label mainQuesContent;

    @FXML
    private Label order;

    @FXML
    private Label subQuesContent;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private Question question;
    private List<Question> selectedQuestions;
    public void setOrder(int order) {
        this.order.setText(String.valueOf(order));
    }

    public void setQuestion(Question question) {
        this.question = question;
        this.mainQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
        this.subQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
    }

    @FXML
    public void deleteThisQuestion(MouseEvent event) {
        VBox questionList = (VBox) this.order.getScene().getRoot().lookup(".question-list");
        questionList.getChildren().remove(Integer.parseInt(this.order.getText()) - 1);
        selectedQuestions.remove(Integer.parseInt(this.order.getText()) - 1);
        Set<Node> orderNumbers = questionList.lookupAll(".order-num");
        int j = 1;
        for(Node orderNum : orderNumbers) {
            Label label = (Label) orderNum;
            label.setText(String.valueOf(j));
            j++;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedQuestions = DataModel.getInstance().getSelectedQuestions();
    }
}
