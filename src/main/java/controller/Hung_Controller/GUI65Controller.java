package controller.Hung_Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI65Controller implements Initializable {

    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
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
