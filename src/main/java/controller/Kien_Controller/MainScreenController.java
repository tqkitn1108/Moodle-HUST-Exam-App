package controller.Kien_Controller;

import controller.Ha_Controller.HeaderController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;

    public void setHeader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/Header.fxml"));
        try {
            Node node = fxmlLoader.load();
            HeaderController gui11Controller = fxmlLoader.getController();
            gui11Controller.hideEditingBtn();
            header.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuizScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizScreen.fxml"));
        try {
            Node node = fxmlLoader.load(); // fxmlLoader chỉ có 1 node là AnchorPane
            QuizScreenController quizScreenController = fxmlLoader.getController();
            quizScreenController.setScreenListener(new NewScreenListener() {
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
        addQuizScreen();
    }
}
