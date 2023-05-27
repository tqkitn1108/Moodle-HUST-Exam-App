package controller.Ha_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class GUI11Controller implements Initializable {
    @FXML
    private AnchorPane listCourse;
    @FXML
    private Button questionViewButt;
    @FXML
    void viewQuestion(MouseEvent ignoredEvent){
        // Xử lý sự kiện khi click button
        questionViewButt.setOnMouseClicked(event -> {
            try {
                // Load giao diện mới từ file GUI21.fxml
                FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
                AnchorPane newAnchorPane = newLoader.load();
                listCourse.getChildren().setAll(newAnchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
//        try {
//            Node node = fxmlLoader.load();
//            listCourse.getChildren().add(node);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
