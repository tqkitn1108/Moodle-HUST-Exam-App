package model;

import java.util.List;

import javafx.scene.image.Image;


public class Question {
    private String questionID;
    private String questionData;
    private Image questionImage;
    private List<String> options;
    private List<Image> optionImages;
    private List<Character> ans;
    private Category category;

    public String getQuestionID() {
        return questionID;
    }
    public String getQuestionData() {
        return questionData;
    }
    public List<String> getOptions() {
        return options;
    }
    public List<Character> getAns() {
        return ans;
    }
    public Category getCategory() {
        return category;
    }
    public Image getQuestionImage() {
        return questionImage;
    }
    public List<Image> getOptionImages() {
        return optionImages;
    }
    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }
    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public void setAns(List<Character> ans) {
        this.ans = ans;
    }
    public void setCategory(Category category) {this.category = category;}
    public void setQuestionImage(Image img) {questionImage = img;}
    public void setOptionImages(List<Image> images) {optionImages = images;}

    public void showQ() {
        System.out.println(questionData);
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
