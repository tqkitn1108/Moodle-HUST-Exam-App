package models;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionID;
    private String questionData;
    private List<String> options;
    private char ans;
    private Quiz quiz;

    public String getQuestionID() {
        return questionID;
    }
    public String getQuestionData() {
        return questionData;
    }
    public List<String> getOptions() {
        return options;
    }
    public char getAns() {
        return ans;
    }
    public Quiz getQuiz() {
        return quiz;
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
    public void setAns(char ans) {
        this.ans = ans;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    Question() {
        questionData = "";
        options = new ArrayList<String>();
        ans = 0;
    }

    public void showQ() {
        System.out.println(questionData);
        int n = options.size();
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println("ANS: " + ans);
    }
}
