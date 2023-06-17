package controller.Kien_Controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
import javafx.scene.paint.Color;
import model.DBInteract;
import model.Question;
import model2.DataModel;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.*;

public class QuestionLayoutController implements Initializable {
    public Label questionNum;
    public Label stateQues;
    public Label flag;
    public VBox questionBox;
    public Label questionContent;
    public AnchorPane correctAnswer;
    private List<Integer> answers;
    private Question question;
    private ToggleGroup choiceGroup;
    private Set<JFXCheckBox> checkBoxGroup;

    public ToggleGroup getChoiceGroup() {
        return choiceGroup;
    }

    public Set<JFXCheckBox> getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
        this.questionContent.setGraphic(new ImageView(image));
    }

    public void setChoices(Question question) {
        choiceGroup = new ToggleGroup();
        answers = new ArrayList<>();
        List<String> options = question.getOptions();
        List<Image> images = question.getOptionImages();
        List<Double> grades = question.getOptionGrades();
        if (!question.isMultipleAnswer()) {
            for (int i = 0; i < options.size(); ++i) {
                JFXRadioButton choice = new JFXRadioButton();
                choice.setText(options.get(i));
                choice.setGraphic(new ImageView(images.get(i)));
                choice.setToggleGroup(choiceGroup);
                if (grades.get(i) > 0) answers.add(i);
                VBox.setMargin(choice, new Insets(6, 10, 0, 20));
                questionBox.getChildren().add(choice);
            }
        } else {
            checkBoxGroup = new LinkedHashSet<>();
            for (int i = 0; i < options.size(); ++i) {
                JFXCheckBox choice = new JFXCheckBox();
                choice.setText(options.get(i));
                choice.setGraphic(new ImageView(images.get(i)));
                checkBoxGroup.add(choice);
                if (grades.get(i) > 0) answers.add(i);
                VBox.setMargin(choice, new Insets(6, 10, 0, 20));
                questionBox.getChildren().add(choice);
            }
        }
    }

    public void setAddAnswer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/CorrectAns.fxml"));
        try {
            Node node = fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 15.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            correctAnswer.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
