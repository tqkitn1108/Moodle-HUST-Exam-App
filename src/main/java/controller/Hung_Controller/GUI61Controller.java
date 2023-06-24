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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Quiz;
import model2.DataModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GUI61Controller implements Initializable {
    @FXML
    private Label quizName;
    @FXML
    private Text timeLimit;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private Quiz quiz;
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.quizName.setText(quiz.getQuizName());
        setTimeLimit(quiz.getTimeLimit());
    }

    public void setTimeLimit(Integer timeLimit) {
        int hrs = timeLimit / 60;
        int mins = timeLimit % 60;
        String hrText = "", minText = "";
        if (hrs == 1) hrText = "1 hour ";
        else if (hrs > 1) hrText = hrs + " hours ";
        if (mins == 1) minText = "1 min ";
        else if (mins > 1) minText = mins + " mins ";
        this.timeLimit.setText("Time limit: " + hrText + minText);
    }

    @FXML
    public void editingQuiz(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62a.fxml"));
            Node node = fxmlLoader.load();
            GUI62aController gui62aController = fxmlLoader.getController();
            gui62aController.setQuiz(quiz);
            gui62aController.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.addAddressToBreadcrumbs("Edit quiz");
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void previewQuiz(ActionEvent event) throws Exception {
        // Cài đặt cho primaryStage khi hiện cửa sổ xác nhận
        Stage thisStage = (Stage) quizName.getScene().getWindow();
        Scene thisScene = thisStage.getScene();
        StackPane overlayStackPane = new StackPane(thisScene.getRoot());
        overlayStackPane.setPrefSize(thisStage.getWidth() - 15, thisStage.getHeight() - 38);
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-opacity: 1;");
        overlayStackPane.getChildren().add(overlay);
        thisScene.setRoot(overlayStackPane);
        thisStage.setScene(thisScene);

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
        if (gui72Controller.isExported) {
            changeScreen(true);
        } else if (gui72Controller.isPreviewed) {
            changeScreen(false);
        }
    }

    private void changeScreen(boolean isExported) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuizScreen.fxml"));
        Node node = fxmlLoader.load();
        QuizScreenController quizScreenController = fxmlLoader.getController();
        quizScreenController.setQuiz(quiz);
        quizScreenController.setMainScreen(this.headerListener, this.screenListener);
        this.headerListener.addAddressToBreadcrumbs("Preview");
        this.screenListener.removeTopScreen();
        this.screenListener.changeScreen(node);
        if (isExported) {
            quizScreenController.hideTimer();
            quizScreenController.exportToPdf();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
