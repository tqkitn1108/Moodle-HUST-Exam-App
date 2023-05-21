package controller.Kien_Controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Quiz;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionLayoutController implements Initializable {
    public Label questionNum;
    public Label stateQues;
    public Label flag;
    public VBox questionBox;
    public Label questionContent;
    public ToggleGroup choice;
    public JFXRadioButton op1;
    public JFXRadioButton op2;
    public JFXRadioButton op3;
    public JFXRadioButton op4;
    public AnchorPane correctAnswer;


    public void setStateQues(String state) {
        this.stateQues.setText(state);
    }

    public void setQuestionNum(Integer number) {
        this.questionNum.setText(number.toString());
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent.setText(questionContent);
    }

    public void setAddAnswer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/CorrectAns.fxml"));
        try {
            Node node = fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 15.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            correctAnswer.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
