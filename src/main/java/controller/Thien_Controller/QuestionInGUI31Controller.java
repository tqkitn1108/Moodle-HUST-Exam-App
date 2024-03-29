package controller.Thien_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionInGUI31Controller implements Initializable {
    @FXML
    private Label quesContent;
    private Question question;
    private String cateNameWithNum;

    private QuestionInGUI31Controller questionInGUI31Controller;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    public void setQuestionInGUI31Controller(QuestionInGUI31Controller questionInGUI31Controller) {
        this.questionInGUI31Controller = questionInGUI31Controller;
    }
    public void setQuestion(Question question) {
        this.question = question;
        this.quesContent.setText(question.getQuestionName()+": " + question.getQuestionText());
    }

    public void setCateNameWithNum(String cateNameWithNum){
        this.cateNameWithNum = cateNameWithNum;
    }

    @FXML
    public void openEditing(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/GUI32.fxml"));
            Node node = fxmlLoader.load();
            GUI32Controller gui32Controller = fxmlLoader.getController();
            gui32Controller.setPageLabel("Editing Multiple choice question");
            gui32Controller.setCateBox(cateNameWithNum);
            gui32Controller.setQuestion(this.question);
            gui32Controller.setQuestionInGUI31Controllers(questionInGUI31Controller);
            gui32Controller.setMainScreen(this.headerListener, this.screenListener);
            this.headerListener.hideMenuButton();
            this.headerListener.hideEditingBtn();
            this.headerListener.addAddressToBreadcrumbs("Question bank");
            this.headerListener.addAddressToBreadcrumbs("Questions");
            this.headerListener.addAddressToBreadcrumbs("Editing a Multiple choice question");
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
