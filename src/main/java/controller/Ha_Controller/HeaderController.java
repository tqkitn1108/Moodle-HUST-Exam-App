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
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    public void backHomePage(ActionEvent event) {
        MFXButton lastBtn = (MFXButton) breadcrumbs.getChildren().get(breadcrumbs.getChildren().size() - 1);
        if (lastBtn.getText().equals("Preview")) {
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
                    courseListController.setMainScreen(this.headerListener, this.screenListener);
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
                courseListController.setMainScreen(this.headerListener, this.screenListener);
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

    public void openQuestionMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            for (int i = 0; i < listBtn.getChildren().size(); ++i) {
                if (listBtn.getChildren().get(i) instanceof Button button) {
                    int finalI = i;
                    button.setOnAction(event -> {
                        menuBtn.hide();
                        gui21Controller.setMainScreen(this.headerListener, this.screenListener);
                        gui21Controller.getTabPane().getSelectionModel().select(finalI);
                        gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
                        this.screenListener.removeTopScreen();
                        this.screenListener.changeScreen(node);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditing(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI51.fxml"));
            Node node = fxmlLoader.load();
            GUI51Controller gui51Controller = fxmlLoader.getController();
            gui51Controller.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.addAddressToBreadcrumbs("Adding a new Quiz");
            this.headerListener.hideMenuButton();
            this.headerListener.hideEditingBtn();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openQuestionMenu();
    }

}
