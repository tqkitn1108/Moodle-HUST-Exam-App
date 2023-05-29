package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable {

    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;
    @FXML
    private MenuButton menuWindow;

    @FXML
    private MFXButton editingBtn;

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
            menuWindow.setVisible(false);
//            hideEditingBtn();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.setScreenListener(new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                }

                @Override
                public void handle(Event event) {

                }
            });
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditing(ActionEvent event) {
        try {
            // Load giao diện mới từ file GUI21.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI51.fxml"));
            Node node = fxmlLoader.load();
            menuWindow.hide();
            GUI51Controller gui51Controller = fxmlLoader.getController();
            gui51Controller.setScreenListener(new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                }

                @Override
                public void handle(Event event) {

                }
            });
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addScreenToStackPane(Node node) {
        this.stackPane.getChildren().add(node);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
