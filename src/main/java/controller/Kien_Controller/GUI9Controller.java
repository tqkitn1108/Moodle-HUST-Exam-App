package controller.Kien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI9Controller implements Initializable {
    @FXML
    private MFXButton encryptBtn;
    @FXML
    private MFXPasswordField passField1;
    @FXML
    private MFXPasswordField passField2;
    @FXML
    private FontAwesomeIconView closeIcon;
    @FXML
    private Label closeLabel;
    public boolean isCancelled = false;
    private String password;

    public String getPassword() {
        return password;
    }

    @FXML
    void coloring(MouseEvent event) {
        closeLabel.setStyle("-fx-background-color: #c70d0d");
        closeIcon.setFill(Color.WHITE);
    }

    @FXML
    void uncoloring(MouseEvent event) {
        closeLabel.setStyle("-fx-background-color: #fff");
        closeIcon.setFill(Paint.valueOf("#888888"));
    }

    @FXML
    public void closeThisWindow(MouseEvent event) {
        isCancelled = true;
        Stage stage = (Stage) encryptBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void encryptPdf(ActionEvent event) {
        this.password = passField1.getText();
    }

    public void checkPassword(MFXPasswordField passwordField1, MFXPasswordField passwordField2) {
        passwordField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordField2.getText().length() > 0 && passwordField2.getText().equals(newValue)) {
                encryptBtn.setDisable(false);
                encryptBtn.setStyle("-fx-background-color: #ff5975;");
            } else {
                encryptBtn.setDisable(true);
                encryptBtn.setStyle("-fx-background-color: #777;");
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkPassword(passField1, passField2);
        checkPassword(passField2, passField1);
    }
}
