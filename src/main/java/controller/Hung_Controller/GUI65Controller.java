package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI65Controller implements Initializable {

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @FXML
    public void closeThisWindow(MouseEvent event) {
        this.screenListener.removeTopScreen();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
