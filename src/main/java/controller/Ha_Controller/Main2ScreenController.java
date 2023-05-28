package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class Main2ScreenController implements Initializable {

    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;

    public void setHeader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Ha_FXML/GUI11.fxml"));
        try {
            Node node = fxmlLoader.load();
            GUI11Controller gui11Controller = fxmlLoader.getController();
            gui11Controller.setScreenListener(new NewScreenListener() {
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
            header.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCourseList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Ha_FXML/CourseList.fxml"));
        try {
            Node node = fxmlLoader.load();
            CourseListController courseListController = fxmlLoader.getController();
            courseListController.setScreenListener(new NewScreenListener() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addScreenToStackPane(Node node) {
        this.stackPane.getChildren().add(node);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHeader();
        addCourseList();
    }
}
