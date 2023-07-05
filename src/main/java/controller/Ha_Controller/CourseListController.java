package controller.Ha_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.Quiz;
import model2.DataModel;

import java.net.URL;
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

    public void addNewQuiz(Quiz quiz) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseName.fxml"));
            Node node = fxmlLoader.load();
            CourseNameController courseNameController = fxmlLoader.getController();
            courseNameController.setQuiz(quiz);
            courseNameController.setMainScreen(this.headerListener, this.screenListener);
            VBox.setMargin(node, new Insets(10,10,10,80));
            quizList.getChildren().add(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadQuizList() {
        DBInteract dbInteract = DataModel.getInstance().getDbInteract();
        List<Quiz> quizzes = dbInteract.getAllQuizzes();
        for (Quiz quiz : quizzes) {
            addNewQuiz(quiz);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
