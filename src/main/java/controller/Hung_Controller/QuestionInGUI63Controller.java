package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI63Controller implements Initializable {
    @FXML
    private Label mainQuesContent;
    @FXML
    private Label subQuesContent;

    public void setQuestion(Question question) {
        this.mainQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
        this.subQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
