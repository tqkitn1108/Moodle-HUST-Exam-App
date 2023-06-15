package controller.Hung_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI62aController implements Initializable {
    @FXML
    private Label title;
    @FXML
    private MenuButton menuBtn;
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

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void addQuestionToScrollPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI64.fxml"));
            Node node = fxmlLoader.load();
            questionList.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openQuestionBank(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI63.fxml"));
            Node node = fxmlLoader.load();
            GUI63Controller gui63Controller = fxmlLoader.getController();
            gui63Controller.setHeaderListener(this.headerListener);
            gui63Controller.setScreenListener(this.screenListener);
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addRandomQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI65.fxml"));
            Node node = fxmlLoader.load();
            GUI65Controller gui65Controller = fxmlLoader.getController();
            gui65Controller.setHeaderListener(this.headerListener);
            gui65Controller.setScreenListener(this.screenListener);
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveEditing(ActionEvent event) {
        try {
            this.headerListener.removeAddress(1);
            this.screenListener.removeTopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
