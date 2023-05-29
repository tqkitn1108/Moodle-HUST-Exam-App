package controller.Ha_Controller;

import controller.Thien_Controller.GUI32Controller;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    @FXML
    void createNewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/GUI32.fxml"));
            Node node = fxmlLoader.load();
            GUI32Controller gui32Controller = fxmlLoader.getController();
            gui32Controller.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


}
