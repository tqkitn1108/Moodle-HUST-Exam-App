package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI21Controller implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private MFXCheckbox checkBox;

    private NewScreenListener screenListener;
    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


}
