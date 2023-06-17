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
import model.DBInteract;
import model.Quiz;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseListController implements Initializable {
    @FXML
    private VBox quizList;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
        loadQuizList();
    }

    public void addNewQuiz(String name, int timeLimit) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseName.fxml"));
            Node node = fxmlLoader.load();
            CourseNameController courseNameController = fxmlLoader.getController();
            courseNameController.setQuizItem(name);
            courseNameController.setTimeLimit(timeLimit);
            courseNameController.setMainScreen(this.headerListener, this.screenListener);
            VBox.setMargin(node, new Insets(15,10,15,80));
            quizList.getChildren().add(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadQuizList() {
        DBInteract dbInteract = new DBInteract();
        List<Quiz> quizzes = dbInteract.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            addNewQuiz(quiz.getQuizName(), quiz.getTimeLimit());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
