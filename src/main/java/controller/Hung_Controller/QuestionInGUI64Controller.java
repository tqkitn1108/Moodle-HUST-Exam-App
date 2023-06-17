package controller.Hung_Controller;

import javafx.fxml.Initializable;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI64Controller implements Initializable {

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
