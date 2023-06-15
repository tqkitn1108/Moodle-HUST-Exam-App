package model;

import javafx.scene.image.Image;

import java.util.List;


public class Question {
    private String questionName;
    private String questionText;
    private Image questionImage;
    private List<String> options;
    private List<Image> optionImages;
    private List<Character> ans;

    public String getQuestionName() {
        return questionName;
    }
    public String getQuestionText() {
        return questionText;
    }
    public List<String> getOptions() {
        return options;
    }
    public List<Character> getAns() {
        return ans;
    }
    public Image getQuestionImage() {
        return questionImage;
    }
    public List<Image> getOptionImages() {
        return optionImages;
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
        this.ans = ans;
    }
    public void setQuestionImage(Image img) {questionImage = img;}
    public void setOptionImages(List<Image> images) {optionImages = images;}

    public void showQ() {
        System.out.println(questionText);
        if (questionImage != null) System.out.print("/image");
        int n = options.size();
        for (int i=0;i<n;i++) {
            System.out.println(options.get(i));
            //if (optionImages.get(i) != null) System.out.print("/img");
        }
        System.out.print("ANS: ");
        for (Character c:ans) System.out.print(String.valueOf(c) + ", ");
    }
}
