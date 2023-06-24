package model2;

import controller.Kien_Controller.QuestionLayoutController;
import controller.Thien_Controller.QuestionInGUI31Controller;
import javafx.scene.Node;
import model.DBInteract;
import model.Question;

import java.util.List;
import java.util.Map;

public class DataModel {
    private static DataModel instance;

    private Integer number;

    private Map<Integer, List<Integer>> userAnswer;

    private DBInteract dbInteract;

    private List<Question> selectedQuestions;

    private List<Node> questionNodes;
    private List<QuestionLayoutController> questionLayoutControllers;

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
}