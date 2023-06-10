package model;

import java.util.List;

public class Quiz {
    private String quizName;
    private String quizDescription;
    private int timeLimit;
    private Category category;
    private List<String> questionsID;


    public String getQuizName() {
        return quizName;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<String> getQuestionsID() {
        return questionsID;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setQuestionsID(List<String> questionsID) {
        this.questionsID = questionsID;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
