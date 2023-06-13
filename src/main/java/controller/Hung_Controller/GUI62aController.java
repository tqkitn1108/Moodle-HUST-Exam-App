package controller.Hung_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI62aController implements Initializable {
    @FXML
    private Label title;
    @FXML
    private MenuButton menuBtn;
    @FXML
    private VBox questionList;
    @FXML
    private Label pencilLabel;

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
        pencilLabel.setText("Page 1");
        pencilLabel.setStyle("-fx-text-fill: #c34d53");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI64.fxml"));
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
