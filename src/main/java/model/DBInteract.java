package model;

import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInteract {

    private Connection conn;

    DBInteract() {
        conn = this.connect();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:data.db";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertQuestion(Question q, String categoryTitle) {

        try {
            String sql = "INSERT INTO QUESTION(questionName,questionText,catID,questionImage) VALUES(?,?," +
                    "(SELECT catID FROM CATEGORY WHERE catTitle = ?),?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, q.getQuestionName());
            pstmt.setString(2, q.getQuestionText());
            pstmt.setString(3,categoryTitle);
            pstmt.setBytes(4,DataInteract.changeImageToBytes(q.getQuestionImage()));
            pstmt.executeUpdate();

            sql = "INSERT INTO OPTIONS(questionName, optionLabel, optionData, optionImage, optionGrade) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);

            int n = q.getOptions().size();
            for (int i = 0;i<n;i++) {
                pstmt.setString(1, q.getQuestionName());
                pstmt.setString(2, String.valueOf(q.getOptionLabels().get(i)));
                pstmt.setString(3, q.getOptions().get(i));
                pstmt.setBytes(4, DataInteract.changeImageToBytes(q.getOptionImages().get(i)));
                pstmt.setDouble(5,q.getOptionGrades().get(i));
                pstmt.executeUpdate();
            }

            pstmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewCategory(String parentCategoryTitle, String categoryID, String categoryTitle) {
        try {
            String sql = "INSERT INTO CATEGORY(catID, catTitle) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,categoryID);
            pstmt.setString(2,categoryTitle);
            pstmt.executeUpdate();

            if (parentCategoryTitle == null) return;
            sql = "INSERT INTO SUBCAT(catID, subCatID) VALUES((SELECT catID FROM CATEGORY WHERE catTitle = ?),?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,parentCategoryTitle);
            pstmt.setString(2,categoryID);
            pstmt.executeUpdate();

            pstmt.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Question getQuestion(String questionName) {
        String sql = "SELECT questionText,questionImage FROM QUESTION WHERE questionName = ?";
        Question q = new Question();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,questionName);
            ResultSet rs = pstmt.executeQuery();
            byte[] bytes;
            if (rs.next()) {
                q.setQuestionName(questionName);
                q.setQuestionText(rs.getString(1));
                bytes = rs.getBytes(2);
                if(bytes != null) q.setQuestionImage(DataInteract.changeBytesToImage(bytes));
                else q.setQuestionImage(null);
            }
            else return null;

            sql = "SELECT optionLabel, optionData, optionImage, optionGrade FROM OPTIONS WHERE questionName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,questionName);
            rs = pstmt.executeQuery();
            List<String> options = new ArrayList<>();
            List<Image> optionImages = new ArrayList<>();
            List<Double> optionGrades = new ArrayList<>();
            List<Character> optionLabels = new ArrayList<>();
            while (rs.next()) {
                optionLabels.add(rs.getString(1).charAt(0));
                options.add(rs.getString(2));
                bytes = rs.getBytes(3);
                if (bytes != null) optionImages.add(DataInteract.changeBytesToImage(bytes));
                else optionImages.add(null);
                optionGrades.add(rs.getDouble(4));
            }
            q.setOptionLabels(optionLabels);
            q.setOptions(options);
            q.setOptionImages(optionImages);
            q.setOptionGrades(optionGrades);

            return q;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Question> getQuestionsBelongToCategory(String categoryTitle) {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "SELECT questionName FROM QUESTION,CATEGORY WHERE catTitle = '" + categoryTitle
                    + "' AND CATEGORY.catID = QUESTION.catID";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Question q = getQuestion(rs.getString(1));
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
        try {
            String sql = "SELECT catID, catTitle FROM CATEGORY";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cats.add(new Category(rs.getString(1),rs.getString(2)));
            }
            stmt.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cats;
    }

    public List<Category> getSubCategoriesOf(String categoryTitle) {
        String sql = "SELECT subCatID,sub.catTitle FROM SUBCAT,CATEGORY parent,CATEGORY sub WHERE SUBCAT.catID = parent.catID " +
                "AND parent.catTitle = ? AND sub.catID = subCatID";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,categoryTitle);
            ResultSet rs = pstmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category cat = new Category(rs.getString(1),rs.getString(2));
                categories.add(cat);
            }
            pstmt.close();
            return categories;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getCategoryID(String categoryTitle) {
        String sql = "SELECT catID FROM CATEGORY WHERE catTitle = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,categoryTitle);
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, quiz.getQuizName());
            pstmt.setString(2, quiz.getQuizDescription());
            pstmt.setInt(3, quiz.getTimeLimit());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addQuestionToQuiz(String quizName, String questionName) {
        String sql = "INSERT INTO QUIZ_QUESTION(quizID, questionName) VALUES((SELECT quizID FROM QUIZ WHERE quizName = ?),?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            pstmt.setString(2,questionName);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Question> getQuestionBelongToQuiz(String quizName) {
        String sql = "SELECT questionName FROM QUIZ, QUIZ_QUESTION WHERE quizName = ? AND QUIZ.quizID = QUIZ_QUESTION.quizID";
        List<Question> questions = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                questions.add(getQuestion(rs.getString(1)));
            }
            pstmt.close();
            return questions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteQuestion(String questionName) throws Exception {
        String sql = "DELETE FROM OPTIONS WHERE questionName = '" + questionName + "'";
        try {
            //Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "DELETE FROM QUIZ_QUESTION WHERE questionName = '" + questionName + "'";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM QUESTION WHERE questionName = '" + questionName + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
    }

    public void deleteCategory(String categoryTitle) throws Exception {
        String sql = "SELECT catID FROM CATEGORY WHERE catTitle = '" + categoryTitle + "'";
        try {
            //Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                sql = "SELECT catTitle, subCatID FROM CATEGORY, SUBCAT WHERE SUBCAT.catID = '"
                        + rs.getString(1) + "' AND SUBCAT.subCatID = CATEGORY.catID";
                Statement stmt1 = conn.createStatement();
                ResultSet subRs = stmt1.executeQuery(sql);

                while (subRs.next()) {
                    sql = "DELETE FROM SUBCAT WHERE subCatID = '" + subRs.getString(2) + "'";
                    Statement stmt2 = conn.createStatement();
                    stmt2.executeUpdate(sql);
                    deleteCategory(subRs.getString(1));
                }

                sql = "SELECT questionName FROM QUESTION WHERE catID = '" + rs.getString(1) + "'";
                subRs = stmt1.executeQuery(sql);
                while (subRs.next()) {
                    deleteQuestion(subRs.getString(1));
                }


            }

            sql = "DELETE FROM CATEGORY WHERE catTitle = '" + categoryTitle + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
    }

    public void deleteQuiz(String quizName) {
        String sql = "DELETE FROM QUIZ_QUESTION WHERE quizID IN (SELECT quizID FROM QUIZ WHERE quizName = ?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            pstmt.executeUpdate();

            sql = "DELETE FROM QUIZ WHERE quizName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,quizName);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Quiz> getAllQuizzes() {
        String sql = "SELECT quizName, quizDescription, timeLimit FROM QUIZ";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Quiz> quizzes = new ArrayList<>();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizName(rs.getString(1));
                quiz.setQuizDescription(rs.getString(2));
                quiz.setTimeLimit(rs.getInt(3));
                quizzes.add(quiz);
            }
            return quizzes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Category> getAllNonSubCategories() {
        String sql = "SELECT catID, catTitle FROM CATEGORY WHERE catID NOT IN (SELECT subCatID catID FROM SUBCAT)";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(rs.getString(1),rs.getString(2)));
            }
            stmt.close();
            return categories;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void editQuestion(String questionName, Question newQuestion) throws Exception {
        String sql = "SELECT catTitle FROM QUESTION,CATEGORY WHERE QUESTION.catID = CATEGORY.catID AND questionName = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,questionName);
        ResultSet catRs = pstmt.executeQuery();

        sql = "SELECT quizName FROM QUIZ_QUESTION, QUIZ WHERE QUIZ_QUESTION.quizID = QUIZ.quizID AND questionName = ?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql);
        pstmt2.setString(1,questionName);
        ResultSet quizRs = pstmt2.executeQuery();

        deleteQuestion(questionName);

        catRs.next();
        insertQuestion(newQuestion, catRs.getString(1));
        while (quizRs.next()) {
            addQuestionToQuiz(quizRs.getString(1),newQuestion.getQuestionName());
        }

    }

    public void treeView(String categoryTitle, int level) {
        for (int i=0;i<level;i++) System.out.print(' ');
        System.out.println(categoryTitle);
        List<Category> subCategories = getSubCategoriesOf(categoryTitle);
        for (Category cat:subCategories) {
            treeView(cat.getCategoryTitle(),level+1);
        }
    }
}
