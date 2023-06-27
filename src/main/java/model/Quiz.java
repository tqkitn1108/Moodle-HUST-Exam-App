package model;

import java.util.List;

public class Quiz {
    private String quizName;
    private String quizDescription;
    private int timeLimit;
    private boolean shuffle;
    private boolean showDescription;

    public boolean isShowDescription() {
        return showDescription;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
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

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<Question> getQuestions() {
        DBInteract dbi = new DBInteract();
        return dbi.getQuestionBelongToQuiz(quizName);
    }
}
