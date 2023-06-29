package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Quiz;
import model2.DataModel;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseNameController implements Initializable {
    @FXML
    private MFXButton quizItem;
    @FXML
    private Label quizDesc;
    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        quizItem.setText(quiz.getQuizName());
        quizDesc.setText(quiz.getQuizDescription());
//        quizDesc.setVisible(false);
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
            DataModel.getInstance().setQuiz(quiz);
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
