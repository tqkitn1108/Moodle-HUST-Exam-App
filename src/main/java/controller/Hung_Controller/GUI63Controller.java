package controller.Hung_Controller;

import controller.Thien_Controller.GUI32Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI63Controller implements Initializable {
    @FXML
    private VBox questionList;

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @FXML
    public void closeThisWindow(MouseEvent event) {
        this.screenListener.removeTopScreen();
    }

    @FXML
    public void addSelectedQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI64.fxml"));
            Node node = fxmlLoader.load();
            GUI64Controller gui64Controller = fxmlLoader.getController();
            gui64Controller.setScreenListener(this.screenListener);
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
