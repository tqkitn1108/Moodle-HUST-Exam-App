package controller.Thien_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI31Controller implements Initializable {

    @FXML
    private HBox question;
    @FXML
    private Label quesContent;

    public void setQuesContent(String quesContent) {
        this.quesContent.setText(quesContent);
    }

    public void setWhiteBackground(int index) {
        if(index % 2 == 1) {
            question.setStyle("-fx-background-color: #fff");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
