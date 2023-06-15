package controller.Kien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.time.LocalDateTime;

import javafx.stage.StageStyle;
import model2.Quiz;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class GUI72Controller implements Initializable {

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    private Label closeBtn;

    @FXML
    private MFXButton startAttemptBtn;

    public boolean isCancelled = false;
    public boolean isPreviewed = false;
    public boolean isExported = false;

    // Xử lý event với closeBtn
    @FXML
    public void closeThisWindow(MouseEvent event) {
        isCancelled = true;
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void coloring(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: #c70d0d");
        closeIcon.setFill(Color.WHITE);
    }

    @FXML
    void uncoloring(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: #fff");
        closeIcon.setFill(Paint.valueOf("#888888"));
    }

    @FXML
    public void startAttempt(ActionEvent event) {
        isPreviewed = true;
    }

    @FXML
    public void exportToPdf(ActionEvent event) {
        isPreviewed = true;
        isExported = true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
