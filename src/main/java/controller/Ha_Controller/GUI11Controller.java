package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GUI11Controller implements Initializable {
    @FXML
    private MenuButton menuWindow;

    @FXML
    private MFXButton editingBtn;

    private NewScreenListener screenListener;
    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public void hideEditingBtn() {
        editingBtn.setVisible(false);
    }

    public void showEditingBtn() {
        editingBtn.setVisible(true);
    }


    @FXML
    private void viewQuestion(MouseEvent ignoredEvent){
            try {
                // Load giao diện mới từ file GUI21.fxml
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
                Node node = fxmlLoader.load();
                menuWindow.hide();
                hideEditingBtn();
                this.screenListener.removeTopScreen();
                this.screenListener.changeScreen(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
