package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI63Controller implements Initializable {
    @FXML
    private Label mainQuesContent;
    @FXML
    private Label subQuesContent;

    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }
    private Question question;
    public void setQuestion(Question question) {
        this.question = question;
        this.mainQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
        this.subQuesContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
