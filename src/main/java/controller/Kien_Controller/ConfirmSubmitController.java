package controller.Kien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmSubmitController implements Initializable {

    @FXML
    private FontAwesomeIconView closeIcon;
    @FXML
    private Label closeSection;
    @FXML
    private Label quizName;

    @FXML
    private MFXButton returnBtn;

    @FXML
    private MFXButton submitBtn;

    public boolean isSubmitted = false;
    public boolean isCancelled = false;

    @FXML
    public void closeThisWindow(MouseEvent event) {
        isCancelled = true;
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void submit(MouseEvent event) {
        isSubmitted = true;
        closeThisWindow(event);
    }

    @FXML
    void coloring(MouseEvent event) {
        closeSection.setStyle("-fx-background-color: #c70d0d");
        closeIcon.setFill(Color.WHITE);
    }

    @FXML
    void uncoloring(MouseEvent event) {
        closeSection.setStyle("-fx-background-color: #fff");
        closeIcon.setFill(Paint.valueOf("#888888"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
