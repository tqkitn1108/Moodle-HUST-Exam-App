package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class HeaderController implements Initializable {

    @FXML
    private MenuButton menuBtn;
    @FXML
    private MFXButton editingBtn;
    @FXML
    private VBox listBtn;
    @FXML
    private HBox breadcrumbs;

    public HBox getBreadcrumbs() {
        return breadcrumbs;
    }

    public MFXButton getEditingBtn() {
        return editingBtn;
    }

    public MenuButton getMenuBtn() {
        return menuBtn;
    }

    private HeaderListener headerListener;

    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public void backHomePage(MouseEvent event) {
        Label lastLabel = (Label) breadcrumbs.getChildren().get(breadcrumbs.getChildren().size() - 1);
        if (lastLabel.getText().equals("Preview")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("You are in the test, are you sure you want to exit?");
            alert.setContentText("When you get rid of the test, it means that you accept the test score not to be recorded.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
                    Node node = fxmlLoader.load();
                    CourseListController courseListController = fxmlLoader.getController();
                    courseListController.setHeaderListener(this.headerListener);
                    courseListController.setScreenListener(this.screenListener);
                    this.headerListener.showMenuButton();
                    this.headerListener.showEditingBtn();
                    this.headerListener.removeAddress(5);
                    this.screenListener.removeTopScreen();
                    this.screenListener.changeScreen(node);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
                Node node = fxmlLoader.load();
                CourseListController courseListController = fxmlLoader.getController();
                courseListController.setHeaderListener(this.headerListener);
                courseListController.setScreenListener(this.screenListener);
                this.headerListener.showMenuButton();
                this.headerListener.showEditingBtn();
                this.headerListener.removeAddress(5);
                this.screenListener.removeTopScreen();
                this.screenListener.changeScreen(node);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void viewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            menuBtn.hide();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.getTabPane().getSelectionModel().select(0);
            gui21Controller.setHeaderListener(this.headerListener);
            gui21Controller.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCategory(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            menuBtn.hide();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.getTabPane().getSelectionModel().select(1);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            gui21Controller.setHeaderListener(this.headerListener);
            gui21Controller.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openImport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            menuBtn.hide();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.getTabPane().getSelectionModel().select(2);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            gui21Controller.setHeaderListener(this.headerListener);
            gui21Controller.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openExport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            menuBtn.hide();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.getTabPane().getSelectionModel().select(3);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            gui21Controller.setHeaderListener(this.headerListener);
            gui21Controller.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditing(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI51.fxml"));
            Node node = fxmlLoader.load();
            GUI51Controller gui51Controller = fxmlLoader.getController();
            gui51Controller.setScreenListener(this.screenListener);
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
