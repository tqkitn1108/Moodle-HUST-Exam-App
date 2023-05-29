package controller.Kien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionRectangleController implements Initializable {

    @FXML
    private Label number;
    @FXML
    private VBox rectangle;
    @FXML
    private Label state;
    @FXML
    private FontAwesomeIconView icon;

    public VBox getRectangle() {
        return rectangle;
    }

    public void setNumber(Integer number) {
        this.number.setText(number.toString());
    }

    public void setDefault() {
        state.setStyle("-fx-background-color: #fff");
        icon.setFill(Paint.valueOf("#fff"));
    }

    public void setAnswered() {
        icon.setFill(Paint.valueOf("#6c757d"));
        state.setStyle("-fx-background-color: #6c757d");
    }

    public void setWrongAnswered() {
        icon.setGlyphName("REMOVE");
        state.setStyle("-fx-background-color:  #E21717");
    }

    public void setRightAnswered() {
        icon.setGlyphName("CHECK");
        state.setStyle("-fx-background-color:  #3DBE29");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
