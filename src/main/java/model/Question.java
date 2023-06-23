package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Question {
    private String questionName;
    private String questionText;
    private Image questionImage;
    private List<String> options;
    private List<Character> optionLabels;
    private List<Image> optionImages;
    private List<Double> optionGrades;

    public String getQuestionName() {
        return questionName;
    }
    public String getQuestionText() {
        return questionText;
    }
    public List<String> getOptions() {
        return options;
    }
    public Image getQuestionImage() {
        return questionImage;
    }
    public List<Image> getOptionImages() {
        return optionImages;
    }

    public List<Double> getOptionGrades() {
        return optionGrades;
    }

    public List<Character> getOptionLabels() {
        return optionLabels;
    }

    public void setOptionLabels(List<Character> optionLabels) {
        this.optionLabels = optionLabels;
    }

    public void setOptionGrades(List<Double> optionGrades) {
        this.optionGrades = optionGrades;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public void setAns(List<Character> ans) {
        List<Double> grades = new ArrayList<>(Collections.nCopies(options.size(),0.0));
        int index = 0;
        while (index < ans.size()) {
            for (int i=0;i<optionLabels.size();i++) {
                if (optionLabels.get(i) == ans.get(index)) {
                    grades.set(i, 100.0 /ans.size());
                    break;
                }
            }
            index++;
        }
        optionGrades = grades;
    }
    public void setQuestionImage(Image img) {questionImage = img;}
    public void setOptionImages(List<Image> images) {optionImages = images;}

    public Boolean isMultipleAnswer() {
        int count = 0;
        for (Double d:optionGrades) {
            if (d>0) count++;
        }
        return (count > 1);
    }

    public void showQ() {
        System.out.print(questionText);
        if (questionImage != null) System.out.println("+ /image");
        else System.out.println();
        int n = options.size();
        for (int i=0;i<n;i++) {
            System.out.print(optionLabels.get(i) + ". " + options.get(i));
            if (optionImages.get(i) != null) System.out.println("+ /img " + optionGrades.get(i));
            else System.out.println(" " + optionGrades.get(i));
        }

        System.out.println();
    }
}
