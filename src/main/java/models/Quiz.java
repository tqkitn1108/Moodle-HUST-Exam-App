package models;

public class Quiz {
    private Integer quizId;
    private String title;

    public Quiz() {
    }

    public Quiz(String title) {
        this.title = title;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Quiz{" + "quizId=" + quizId + ", title=" + title + '}';
    }
}
