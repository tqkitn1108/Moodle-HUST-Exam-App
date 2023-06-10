package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseListController implements Initializable {
    @FXML
    private VBox quizList;
    private HeaderListener headerListener;
    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }
    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public void openQuiz() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI61.fxml"));
            Node node = fxmlLoader.load();
            GUI61Controller gui61Controller = fxmlLoader.getController();
            for (Node node1 : quizList.getChildren()) {
                if (node1 instanceof MFXButton) {
                    MFXButton button = (MFXButton) node1;
                    button.setOnAction(event -> {
                        gui61Controller.setQuizName(button.getText());
                        this.headerListener.addAddressToBreadcrumbs("General");
                        this.headerListener.addAddressToBreadcrumbs(button.getText());
                        this.headerListener.hideMenuButton();
                        this.headerListener.hideEditingBtn();
                        gui61Controller.setScreenListener(this.screenListener);
                        gui61Controller.setHeaderListener(this.headerListener);
                        this.screenListener.removeTopScreen();
                        this.screenListener.changeScreen(node);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewQuiz(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseName.fxml"));
            Node node = fxmlLoader.load();
            MFXButton button = (MFXButton) node;
            button.setText(name);
            VBox.setMargin(button, new Insets(15,10,15,80));
            quizList.getChildren().add(button);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openQuiz();
    }
}
