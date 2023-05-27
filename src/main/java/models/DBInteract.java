package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInteract {

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:test.db";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertQuestion(Question q) {

        try {
            String sql = "INSERT INTO QUESTION(questionID,questionData,quizID) VALUES(?,?,?)";
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, q.getQuestionID());
            pstmt.setString(2, q.getQuestionData());
            pstmt.setString(3,q.getQuiz().getQuizID());
            pstmt.executeUpdate();

            sql = "INSERT INTO OPTIONS(questionID, optionLabel, optionData) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);

            for (String options:q.getOptions()) {
                pstmt.setString(1, q.getQuestionID());
                pstmt.setString(2, String.valueOf(options.charAt(0)));
                pstmt.setString(3,options.substring(3));
                pstmt.executeUpdate();
            }

            sql = "INSERT INTO ANSWER(questionID, optionLabel) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,q.getQuestionID());
            pstmt.setString(2, String.valueOf(q.getAns()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void show() {
        String sql = "SELECT questionID, questionData FROM QUESTION";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                String s = rs.getString("questionID");
                System.out.println(rs.getString("questionData"));

                String opt = "SELECT optionLabel, optionData FROM OPTIONS WHERE questionID = '" + s +"'";
                //System.out.println(opt);
                Statement optStmt = conn.createStatement();
                ResultSet optRs = optStmt.executeQuery(opt);
                while (optRs.next()) {
                    System.out.println(optRs.getString(1) + ": " + optRs.getString(2));
                }

                String ans = "SELECT optionLabel FROM ANSWER WHERE questionID = '" + s +"'";
                Statement ansStmt = conn.createStatement();
                ResultSet ansRs = ansStmt.executeQuery(ans);
                ansRs.next();
                System.out.println("ANSWER: " + ansRs.getString(1));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewQuiz(Quiz quiz) {
        try {
            Connection conn = this.connect();
            String sql = "INSERT INTO QUIZ(quizID, quizTitle) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quiz.getQuizID());
            pstmt.setString(2,quiz.getQuizTitle());
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Question> getQuestionsBelongToQuiz(Quiz quiz) {
        List<Question> questions = new ArrayList<Question>();
        try (Connection conn = this.connect()) {
            String sql = "SELECT questionID, questionData FROM QUESTION WHERE quizID = '" + quiz.getQuizID() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question();
                q.setQuiz(quiz);
                String s = rs.getString("questionID");
                q.setQuestionID(s);
                //System.out.println(rs.getString("questionData"));
                q.setQuestionData(rs.getString(2));

                String opt = "SELECT optionLabel, optionData FROM OPTIONS WHERE questionID = '" + s +"'";
                //System.out.println(opt);
                Statement optStmt = conn.createStatement();
                ResultSet optRs = optStmt.executeQuery(opt);

                List<String> options = new ArrayList<String>();
                while (optRs.next()) {
                    //System.out.println(optRs.getString(1) + ": " + optRs.getString(2));
                    options.add(optRs.getString(1) + ": " + optRs.getString(2));
                }
                q.setOptions(options);

                String ans = "SELECT optionLabel FROM ANSWER WHERE questionID = '" + s +"'";
                Statement ansStmt = conn.createStatement();
                ResultSet ansRs = ansStmt.executeQuery(ans);
                ansRs.next();
                //System.out.println("ANSWER: " + ansRs.getString(1));
                q.setAns(ansRs.getString(1).charAt(0));

                questions.add(q);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<Quiz>();
        try (Connection conn = this.connect()) {
            String sql = "SELECT quizID, quizTitle FROM QUIZ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                quizzes.add(new Quiz(rs.getString(1),rs.getString(2)));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }
}
