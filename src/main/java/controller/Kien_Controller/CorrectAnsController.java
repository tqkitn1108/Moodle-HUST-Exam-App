package controller.Kien_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model2.Choice;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CorrectAnsController implements Initializable {
    @FXML
    private VBox answerList;

    public void setAnswerList(List<Character> labels, List<Choice> choices, List<Integer> correctAnswerList) {
        for (Integer i : correctAnswerList) {
            Label answerLabel = new Label(labels.get(i) + ". " + choices.get(i).getOption());
            answerLabel.setGraphic(new ImageView(choices.get(i).getOptionImage()));
            answerLabel.setStyle("-fx-text-fill: #bb7b2e");
            answerLabel.setWrapText(true);
            answerList.getChildren().add(answerLabel);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
