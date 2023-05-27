package models;

import java.sql.*;

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

    public void insert(Question q) {

        try {
            String sql = "INSERT INTO QUESTION(qID,qData,quizID) VALUES(?,?,?)";
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, q.getQuestionID());
            pstmt.setString(2, q.getQuestionData());
            pstmt.setString(3,q.getQuiz().getQuizID());
            pstmt.executeUpdate();

            sql = "INSERT INTO OPTIONS(qID, optionLabel, optionData) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);

            for (String options:q.getOptions()) {
                pstmt.setString(1, q.getQuestionID());
                pstmt.setString(2, String.valueOf(options.charAt(0)));
                pstmt.setString(3,options.substring(3));
                pstmt.executeUpdate();
            }

            sql = "INSERT INTO ANSWER(qID, optionLabel) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,q.getQuestionID());
            pstmt.setString(2, String.valueOf(q.getAns()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void show() {
        String sql = "SELECT qID, qData FROM QUESTION";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                String s = rs.getString("qID");
                System.out.println(rs.getString("qData"));

                String opt = "SELECT optionLabel, optionData FROM OPTIONS WHERE qID = '" + s +"'";
                //System.out.println(opt);
                Statement optStmt = conn.createStatement();
                ResultSet optRs = optStmt.executeQuery(opt);
                while (optRs.next()) {
                    System.out.println(optRs.getString(1) + ": " + optRs.getString(2));
                }

                String ans = "SELECT optionLabel FROM ANSWER WHERE qID = '" + s +"'";
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
}
