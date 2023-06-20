package controller.Hung_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class JumpBoxController implements Initializable {

    @FXML
    private ComboBox<String> jumpBox;

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }
    @FXML
    public void changeScreen(ActionEvent event) {
        if (jumpBox.getValue().equals("Thi cuối kỳ")){

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
