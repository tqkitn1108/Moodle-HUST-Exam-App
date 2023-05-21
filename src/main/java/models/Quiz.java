package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz {
    private Integer quizId;
    private String title;

    public static class MetaData {
        public static final String TABLE_NAME = "quizzes";
        public static final String QUIZ_ID = "quiz_id";
        public static final String TITLE = "title";
    }

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

    // ----------------------------------------------------------------
    public static void createTable() { // Vid #18, #19
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int save() { // Vid #23, #24
        return -1;
    }

    public boolean save(ArrayList<Question> questions) { // Vid #25
        boolean flag = false;
        this.quizId = this.save();

        for(Question q: questions) {
            q.save();
        }

        return flag;
    }

    public static void getAll() { // Vid #44
        Map<Quiz, List<Question>> quizzes = new HashMap<>();
        Quiz key = null;

    }

    // Map một Quiz với số câu hỏi của quiz đấy
    public static Map<Quiz, Integer> getAllWithQuestionCount() { // Vid #51
        Map<Quiz, Integer> quizzes = new HashMap<>();
        Quiz key = null;

        return quizzes;
    }

    public List<Question> getQuestions() {
        List<Question> quiz = new ArrayList<>();

        return quiz;
    }
}
