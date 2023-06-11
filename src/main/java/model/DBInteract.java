package model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInteract {

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/resources/data.db";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertQuestion(Question q) {

        try {
            String sql = "INSERT INTO QUESTION(questionID,questionData,catID,questionImage) VALUES(?,?,?,?)";
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, q.getQuestionID());
            pstmt.setString(2, q.getQuestionData());
            pstmt.setString(3,q.getCategory().getCategoryID());
            pstmt.setBytes(4,DataInteract.changeImageToBytes(q.getQuestionImage()));
            pstmt.executeUpdate();

            sql = "INSERT INTO OPTIONS(questionID, optionLabel, optionData, optionImage) VALUES(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            int n = q.getOptions().size();
            for (int i = 0;i<n;i++) {
                pstmt.setString(1, q.getQuestionID());
                pstmt.setString(2, String.valueOf(q.getOptions().get(i).charAt(0)));
                pstmt.setString(3, q.getOptions().get(i).substring(3));
                pstmt.setBytes(4, DataInteract.changeImageToBytes(q.getOptionImages().get(i)));
                pstmt.executeUpdate();
            }

            sql = "INSERT INTO ANSWER(questionID, optionLabel) VALUES(?,?)";
            pstmt = conn.prepareStatement(sql);
            for (Character c : q.getAns()) {
                pstmt.setString(1, q.getQuestionID());
                pstmt.setString(2, String.valueOf(c));
                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewCategory(String parentCategoryTitle, String categoryTitle) {
        try {
            Connection conn = this.connect();
            String sql = "INSERT INTO CATEGORY(catTitle) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //pstmt.setString(1,categoryID);
            pstmt.setString(1,categoryTitle);
            pstmt.executeUpdate();

            if (parentCategoryTitle == null) return;
            sql = "INSERT INTO SUBCAT(catID, subCatID) SELECT CAT1.catID catID, CAT2.catID subCatID FROM CATEGORY CAT1, CATEGORY CAT2 WHERE CAT1.catTitle = ? AND CAT2.catTitle = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,parentCategoryTitle);
            pstmt.setString(2,categoryTitle);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Question> getQuestionsBelongToCategory(Category category) {
        List<Question> questions = new ArrayList<Question>();
        try (Connection conn = this.connect()) {
            String sql = "SELECT questionID, questionData FROM QUESTION WHERE catID = '" + category.getCategoryID() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question();
                q.setCategory(category);
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
                List<Character> answers = new ArrayList<>();
                while (ansRs.next()) {
                    answers.add(ansRs.getString(1).charAt(0));
                }
                //System.out.println("ANSWER: " + ansRs.getString(1));
                q.setAns(answers);

                questions.add(q);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }

    public List<Category> getAllCategories() {
        List<Category> cats = new ArrayList<>();
        try (Connection conn = this.connect()) {
            String sql = "SELECT catID, catTitle FROM CATEGORY";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cats.add(new Category(rs.getString(1),rs.getString(2)));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cats;
    }

    public List<Category> getSubCategoriesOf(Category cat) {
        String sql = "SELECT subCatID FROM SUBCAT WHERE catID = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,cat.getCategoryID());
            ResultSet rs = pstmt.executeQuery();

            List<Category> cats = new ArrayList<>();
            sql = "SELECT catTitle FROM CATEGORY WHERE catID = ?";
            pstmt = conn.prepareStatement(sql);
            while (rs.next()) {
                pstmt.setString(1,rs.getString(1));
                ResultSet ansRs = pstmt.executeQuery();
                ansRs.next();
                cats.add(new Category(rs.getString(1),ansRs.getString(1)));
            }
            pstmt.close();
            return cats;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getCategoryID(String catTitle) {
        String sql = "SELECT catID FROM CATEGORY WHERE catTitle = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,catTitle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString(1);
            else return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void createNewQuiz(Quiz quiz) {
        String sql = "INSERT INTO QUIZ(quizName, quizDescription, timeLimit) VALUES(?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quiz.getQuizName());
            pstmt.setString(2,quiz.getQuizDescription());
            pstmt.setInt(3,quiz.getTimeLimit());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addQuestionToQuiz(String quizName, String questionID) {
        String sql = "INSERT INTO QUIZ_QUESTION(quizID, questionID) VALUES((SELECT quizID FROM QUIZ WHERE quizName = ?),?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            pstmt.setString(2,questionID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Question getQuestion(String questionID) {
        String sql = "SELECT questionData,questionImage FROM QUESTION WHERE questionID = ?";
        Question q = new Question();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,questionID);
            ResultSet rs = pstmt.executeQuery();
            byte[] bytes;
            if (rs.next()) {
                q.setQuestionID(questionID);
                q.setQuestionData(rs.getString(1));
                bytes = rs.getBytes(2);
                if(bytes != null) q.setQuestionImage(DataInteract.changeBytesToImage(bytes));
                else q.setQuestionImage(null);
            }
            else return null;

            sql = "SELECT optionLabel, optionData, optionImage FROM OPTIONS WHERE questionID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,questionID);
            rs = pstmt.executeQuery();
            List<String> options = new ArrayList<>();
            List< Image> optionImages = new ArrayList<>();
            while (rs.next()) {
                options.add(rs.getString(1) + ": " + rs.getString(2));
                bytes = rs.getBytes(3);
                if (bytes != null) optionImages.add(DataInteract.changeBytesToImage(bytes));
                else optionImages.add(null);
            }
            q.setOptions(options);
            q.setOptionImages(optionImages);

            sql = "SELECT optionLabel FROM ANSWER WHERE questionID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,questionID);
            rs = pstmt.executeQuery();
            List<Character> ans = new ArrayList<>();
            while(rs.next()) {
                ans.add(rs.getString(1).charAt(0));
            }
            q.setAns(ans);

            pstmt.close();
            conn.close();
            return q;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Question> getQuestionBelongToQuiz(String quizName) {
        String sql = "SELECT questionID FROM QUIZ, QUIZ_QUESTION WHERE quizName = ? AND QUIZ.quizID = QUIZ_QUESTION.quizID";
        List<Question> questions = new ArrayList<>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                questions.add(getQuestion(rs.getString(1)));
            }
            pstmt.close();
            conn.close();
            return questions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
