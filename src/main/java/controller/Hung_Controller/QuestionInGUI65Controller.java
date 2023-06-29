package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import model.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI65Controller implements Initializable {
    @FXML
    private HBox parentBox;
    @FXML
    private Label questionContent;
    public void setQuestionContent(Question question) {
        this.questionContent.setText(question.getQuestionName() + ": " + question.getQuestionText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BorderStroke borderStroke = new BorderStroke(Paint.valueOf("#aaaaaa"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, 0, 1, 0));
        Border border = new Border(borderStroke);
        parentBox.setBorder(border);
    }
}
