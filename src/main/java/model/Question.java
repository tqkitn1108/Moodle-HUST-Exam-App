package model;

import javafx.scene.image.Image;
import model2.Choice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Question {
    private String questionName;
    private String questionText;
    private Image questionImage;
    private List<Character> optionLabels;
    private List<Choice> choices;
    private String mediaPath;

    public String getMediaPath() {
        return mediaPath;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getQuestionName() {
        return questionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Image getQuestionImage() {
        return questionImage;
    }

    public List<Character> getOptionLabels() {
        return optionLabels;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setOptionLabels(List<Character> optionLabels) {
        this.optionLabels = optionLabels;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAns(List<Character> ans) {
        List<Double> grades = new ArrayList<>(Collections.nCopies(choices.size(), 0.0));
        int index = 0;
        while (index < ans.size()) {
            for (int i = 0; i < optionLabels.size(); i++) {
                if (optionLabels.get(i) == ans.get(index)) {
                    grades.set(i, 100.0 / ans.size());
                    break;
                }
            }
            index++;
        }
        for (int i = 0; i < choices.size(); ++i) {
            choices.get(i).setOptionGrade(grades.get(i));
        }
    }

    public void setQuestionImage(Image img) {
        questionImage = img;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Boolean isMultipleAnswer() {
        int count = 0;
        for (Choice choice : choices) {
            if (choice.getOptionGrade() > 0) count++;
        }
        return (count > 1);
    }

    public void showQ() {
        System.out.print(questionText);
        if (questionImage != null) System.out.println("+ /image");
        else System.out.println();
        int n = choices.size();
        for (int i = 0; i < n; i++) {
            System.out.print(optionLabels.get(i) + ". " + choices.get(i).getOption());
            if (choices.get(i).getOptionImage() != null) System.out.println("+ /img " + choices.get(i).getOptionGrade());
            else System.out.println(" " + choices.get(i).getOptionGrade());
        }

        System.out.println();
    }
}
