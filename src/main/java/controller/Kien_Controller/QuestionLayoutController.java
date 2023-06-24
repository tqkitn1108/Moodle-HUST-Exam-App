package controller.Kien_Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Question;
import model2.Choice;

import java.net.URL;
import java.util.*;

public class QuestionLayoutController implements Initializable {
    public Label questionNum;
    public Label stateQues;
    public Label flag;
    public VBox questionBox;
    public Label questionContent;
    public AnchorPane correctAnswerPane;
    private List<Integer> correctAnswerList;
    private ToggleGroup choiceGroup;
    private Set<JFXCheckBox> checkBoxGroup;

    public ToggleGroup getChoiceGroup() {
        return choiceGroup;
    }

    public Set<JFXCheckBox> getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public List<Integer> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setQuestion(Question question) {
        setQuestionContent(question.getQuestionName() + ": " + question.getQuestionText(), question.getQuestionImage());
        setChoices(question);
    }

    public void setStateQues(String state) {
        this.stateQues.setText(state);
    }

    public void setQuestionNum(Integer number) {
        this.questionNum.setText(number.toString());
    }

    public void setQuestionContent(String questionContent, Image image) {
        this.questionContent.setText(questionContent);
        this.questionContent.setWrapText(true);
        this.questionContent.setGraphic(new ImageView(image));
    }

    public void setChoices(Question question) {
        choiceGroup = new ToggleGroup();
        correctAnswerList = new ArrayList<>();
        List<Choice> choices = question.getChoices();
        List<Character> labels = question.getOptionLabels();
        if (!question.isMultipleAnswer()) {
            for (int i = 0; i < choices.size(); ++i) {
                JFXRadioButton choice = new JFXRadioButton();
                choice.setText(labels.get(i) + ". " + choices.get(i).getOption());
                choice.setGraphic(new ImageView(choices.get(i).getOptionImage()));
                choice.setToggleGroup(choiceGroup);
                choice.setWrapText(true);
                if (choices.get(i).getOptionGrade() > 0) correctAnswerList.add(i);
                VBox.setMargin(choice, new Insets(6, 20, 0, 20));
                questionBox.getChildren().add(choice);
            }
        } else {
            checkBoxGroup = new LinkedHashSet<>();
            for (int i = 0; i < choices.size(); ++i) {
                JFXCheckBox choice = new JFXCheckBox();
                choice.setText(labels.get(i) + ". " + choices.get(i).getOption());
                choice.setGraphic(new ImageView(choices.get(i).getOptionImage()));
                checkBoxGroup.add(choice);
                choice.setWrapText(true);
                if (choices.get(i).getOptionGrade() > 0) correctAnswerList.add(i);
                VBox.setMargin(choice, new Insets(6, 20, 0, 20));
                questionBox.getChildren().add(choice);
            }
        }
    }

    public void setAddAnswer(Question question) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/CorrectAns.fxml"));
        try {
            Node node = fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 15.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            CorrectAnsController correctAnsController = fxmlLoader.getController();
            correctAnsController.setAnswerList(question.getOptionLabels(), question.getChoices(), correctAnswerList);
            correctAnswerPane.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
