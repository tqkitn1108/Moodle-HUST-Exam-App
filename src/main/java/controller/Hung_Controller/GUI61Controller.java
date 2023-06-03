package controller.Hung_Controller;

import controller.Kien_Controller.GUI72Controller;
import controller.Kien_Controller.QuizScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.NewScreenListener;
import model2.DataModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class GUI61Controller implements Initializable {
    @FXML
    private Label quizName;
    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public void setQuizName(String quizName) {
        this.quizName.setText(quizName);
    }

    @FXML
    public void editingQuiz(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62a.fxml"));
            Node node = fxmlLoader.load();
            GUI62aController gui62aController = fxmlLoader.getController();
            gui62aController.setTitle("Editing quiz: " + this.quizName.getText());
            gui62aController.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void previewQuiz(ActionEvent event) throws IOException {
        // Cài đặt cho primaryStage khi hiện cửa sổ xác nhận
        Stage thisStage = (Stage) quizName.getScene().getWindow();
        Scene thisScene = thisStage.getScene();
        StackPane overlayStackPane = new StackPane(thisScene.getRoot());
        overlayStackPane.setPrefSize(thisStage.getWidth() - 15, thisStage.getHeight() - 38);
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-opacity: 1;");
        overlayStackPane.getChildren().add(overlay);
        Scene newScene = new Scene(overlayStackPane);
        thisStage.setScene(newScene);

        // Cài đặt cho confirm window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Kien_FXML/GUI72.fxml"));
        Parent confirmPane = loader.load();
        Scene scene = new Scene(confirmPane);
        Stage confirmStage = new Stage();
        confirmStage.initStyle(StageStyle.UNDECORATED);
        confirmStage.initModality(Modality.WINDOW_MODAL);
        confirmStage.initOwner(thisStage);

        confirmStage.setScene(scene);
        confirmStage.showAndWait();

        GUI72Controller gui72Controller = loader.getController();
        if (gui72Controller.isCancelled) {
            overlayStackPane.getChildren().remove(overlay);
        }
        if (gui72Controller.isPreviewed) {
            changeScreen();
        }
    }

    private void changeScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizScreen.fxml"));
        try {
            Node node = fxmlLoader.load();
            QuizScreenController quizScreenController = fxmlLoader.getController();
            quizScreenController.setScreenListener(this.screenListener);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
