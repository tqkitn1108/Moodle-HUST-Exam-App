package controller.Ha_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable {
    @FXML
    private Label PopUpButton;
    @FXML
    private AnchorPane listCourse;

    @FXML
    void PopUp(MouseEvent event){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
        try {
            Node node = fxmlLoader.load();
            listCourse.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
