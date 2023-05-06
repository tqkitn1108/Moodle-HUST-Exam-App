package controller.Kien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class GUI72Controller implements Initializable {

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    private MFXButton cancelBtn;

    @FXML
    private Label closeBtn;

    @FXML
    private MFXButton startAttemptBtn;

    // Xử lý event với closeBtn
    @FXML
    public void closeThisWindow(MouseEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void coloring(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: #c70d0d");
        closeIcon.setFill(Color.WHITE);
    }

    @FXML
    void uncoloring(MouseEvent event) {
        closeBtn.setStyle("-fx-background-color: #fff");
        closeIcon.setFill(Paint.valueOf("#888888"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelBtn.setOnAction(event -> {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
        startAttemptBtn.setOnAction(event -> {
            Stage stage = (Stage) startAttemptBtn.getScene().getWindow();
            stage.close();
            Stage fullscreenStage = new Stage();
//            fullscreenStage.setFullScreen(true);
            VBox root = new VBox();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizScreen.fxml"));
            try {
                Node node = fxmlLoader.load();
                root.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(root.getHeight() + " " + root.getWidth());
            Scene scene = new Scene(root);
            fullscreenStage.setScene(scene);
            fullscreenStage.show();
        });
    }
}
