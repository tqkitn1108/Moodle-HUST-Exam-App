package controller.Kien_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizScreenController implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private HBox userSection;

    @FXML
    private VBox progressContent;

    @FXML
    private FlowPane progressPane;

    @FXML
    private VBox quizListContainer;

    Border border = new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 0, 1)));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<20; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }

            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userSection.setBorder(border);
        progressContent.setBorder(new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 1, 1))));
    }
}
