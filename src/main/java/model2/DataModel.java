package model2;

import controller.Ha_Controller.GUI21Controller;
import controller.Kien_Controller.QuestionLayoutController;
import controller.Thien_Controller.QuestionInGUI31Controller;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import model.DBInteract;
import model.Question;
import model.Quiz;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class DataModel {
    private static DataModel instance;

    private Integer number;

    private Map<Integer, List<Integer>> userAnswer;

    private DBInteract dbInteract;

    private List<Question> selectedQuestions;

    private List<Node> questionNodes;
    private List<QuestionLayoutController> questionLayoutControllers;

    private GUI21Controller gui21Controller;

    private Label quantityLabel;
    private Text totalMark;
    private Timer timer;
    private Quiz quiz;

    private String userName;

    private DataModel() {

    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setUserAnswer(Map<Integer, List<Integer>> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Map<Integer, List<Integer>> getUserAnswer() {
        return userAnswer;
    }

    public void setDbInteract(DBInteract dbInteract) {
        this.dbInteract = dbInteract;
    }

    public DBInteract getDbInteract() {
        return dbInteract;
    }

    public void setSelectedQuestions(List<Question> selectedQuestions) {
        this.selectedQuestions = selectedQuestions;
    }

    public List<Question> getSelectedQuestions() {
        return selectedQuestions;
    }

    public void setQuestionNodes(List<Node> nodes) {
        this.questionNodes = nodes;
    }

    public List<Node> getQuestionNodes() {
        return questionNodes;
    }

    public void setQuestionLayoutControllers(List<QuestionLayoutController> questionLayoutControllers) {
        this.questionLayoutControllers = questionLayoutControllers;
    }

    public List<QuestionLayoutController> getQuestionLayoutControllers() {
        return questionLayoutControllers;
    }

    public void setGui21Controller(GUI21Controller gui21Controller) {
        this.gui21Controller = gui21Controller;
    }

    public GUI21Controller getGui21Controller() {
        return gui21Controller;
    }

    public void setQuantityLabel(Label quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public Label getQuantityLabel() {
        return quantityLabel;
    }

    public void setTotalMark(Text totalMark) {
        this.totalMark = totalMark;
    }

    public Text getTotalMark() {
        return totalMark;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}