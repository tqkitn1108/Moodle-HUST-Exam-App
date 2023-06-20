package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Quiz;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseNameController implements Initializable {
    @FXML
    private MFXButton quizItem;
    private Quiz quiz;
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.quizItem.setText(quiz.getQuizName());
    }

    private HeaderListener headerListener;

    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    @FXML
    public void openQuiz(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI61.fxml"));
            Node node = fxmlLoader.load();
            GUI61Controller gui61Controller = fxmlLoader.getController();
            gui61Controller.setQuiz(quiz);
            gui61Controller.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.addAddressToBreadcrumbs("General");
            this.headerListener.addAddressToBreadcrumbs(quizItem.getText());
            this.headerListener.hideMenuButton();
            this.headerListener.hideEditingBtn();
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
