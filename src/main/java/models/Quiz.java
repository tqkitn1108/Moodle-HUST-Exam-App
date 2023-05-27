package models;

public class Quiz {
    private String quizID;
    private String quizTitle;

    public String getQuizID() {
        return quizID;
    }
    public String getQuizTitle() {
        return quizTitle;
    }
    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
    Quiz(String quizID, String quizTitle) {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
    }

}
