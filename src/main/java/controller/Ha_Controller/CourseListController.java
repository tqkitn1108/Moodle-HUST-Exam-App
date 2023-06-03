package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import controller.Thien_Controller.GUI32Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseListController implements Initializable {
    @FXML
    private VBox quizList;
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
                if (node1 instanceof Label) {
                    Label label = (Label) node1;
                    label.setOnMouseClicked(event -> {
                        gui61Controller.setScreenListener(this.screenListener);
                        gui61Controller.setQuizName(label.getText());
                        this.screenListener.removeTopScreen();
                        this.screenListener.changeScreen(node);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openQuiz();
    }
}
