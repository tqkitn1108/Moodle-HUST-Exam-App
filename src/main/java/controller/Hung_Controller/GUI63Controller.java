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
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI63Controller implements Initializable {
    @FXML
    private VBox questionList;

    private HeaderListener headerListener;

    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62a.fxml"));
            Node node = fxmlLoader.load();
            GUI62aController gui62aController = fxmlLoader.getController();
            gui62aController.addQuestionToScrollPane();
            gui62aController.setHeaderListener(this.headerListener);
            gui62aController.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();  // Xóa giao diện GUI63
            this.screenListener.removeTopScreen(); // Xóa giao diện GUI62a đã có
            this.screenListener.changeScreen(node); // Load giao diện GUI62a hiện tại
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
