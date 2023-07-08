package model;

import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInteract {

    public DBInteract() {}

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/resources/data.db";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertQuestion(Question q, String categoryTitle, Connection conn) throws Exception{
        boolean check = false;
        if (conn == null) {
            conn = this.connect();
            check = true;
        }
        String sql = "INSERT INTO QUESTION(questionName,questionText,catID,questionImage,mediaPath) VALUES(?,?," +
                "(SELECT catID FROM CATEGORY WHERE catTitle = ?),?,?)";
        String sql2 = "INSERT INTO OPTIONS(questionName, optionLabel, optionData, optionImage, optionGrade) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);){

            pstmt.setString(1, q.getQuestionName());
            pstmt.setString(2, q.getQuestionText());
            pstmt.setString(3, categoryTitle);
            pstmt.setBytes(4, DataInteract.changeImageToBytes(q.getQuestionImage()));
            pstmt.setString(5,q.getMediaPath());
            pstmt.executeUpdate();

            int n = q.getChoices().size();
            for (int i = 0; i < n; i++) {
                pstmt2.setString(1, q.getQuestionName());
                pstmt2.setString(2, String.valueOf(q.getOptionLabels().get(i)));
                pstmt2.setString(3, q.getChoices().get(i).getOption());
                pstmt2.setBytes(4, DataInteract.changeImageToBytes(q.getChoices().get(i).getOptionImage()));
                pstmt2.setDouble(5, q.getChoices().get(i).getOptionGrade());
                pstmt2.executeUpdate();
            }
        }
        catch (SQLException e) {
            if (e.getMessage().contains("[SQLITE_CONSTRAINT_PRIMARYKEY]") && e.getMessage().contains("QUESTION.questionName")) {
                throw new NameDuplicateException("Question name duplicate!");
            }
        }
        if (check) conn.close();
    }

    public void createNewCategory(String parentCategoryTitle, String categoryID, String categoryTitle) throws Exception{
        String sql = "INSERT INTO CATEGORY(catID, catTitle) VALUES(?,?)";
        String sql2 = "INSERT INTO SUBCAT(catID, subCatID) VALUES((SELECT catID FROM CATEGORY WHERE catTitle = ?),?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);){

            pstmt.setString(1, categoryID);
            pstmt.setString(2, categoryTitle);
            pstmt.executeUpdate();

            if (parentCategoryTitle == null) return;
            pstmt2.setString(1, parentCategoryTitle);
            pstmt2.setString(2, categoryID);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("[SQLITE_CONSTRAINT_PRIMARYKEY]") && e.getMessage().contains("CATEGORY.catID")) {
                throw new NameDuplicateException("Category ID duplicate!");
            }
            else if (e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]") && e.getMessage().contains("CATEGORY.catTitle")) {
                throw new NameDuplicateException("Category title duplicate");
            }
        }
    }

    public Question getQuestion(String questionName) {
        String sql = "SELECT questionText,questionImage,mediaPath FROM QUESTION WHERE questionName = ?";
        String sql2 = "SELECT optionLabel, optionData, optionImage, optionGrade FROM OPTIONS WHERE questionName = ?";
        Question q = new Question();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);){

            pstmt.setString(1, questionName);
            ResultSet rs = pstmt.executeQuery();
            byte[] bytes;
            if (rs.next()) {
                q.setQuestionName(questionName);
                q.setQuestionText(rs.getString(1));
                bytes = rs.getBytes(2);
                if (bytes != null) q.setQuestionImage(DataInteract.changeBytesToImage(bytes));
                else q.setQuestionImage(null);
                q.setMediaPath(rs.getString(3));
            } else return null;

            pstmt2.setString(1, questionName);
            rs = pstmt2.executeQuery();
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
            List<Choice> choices = new ArrayList<>();
            for (int i = 0; i < options.size(); ++i) {
                Choice choice = new Choice();
                choice.setOption(options.get(i));
                choice.setOptionImage(optionImages.get(i));
                choice.setOptionGrade(optionGrades.get(i));
                choices.add(choice);
            }
            q.setChoices(choices);

            return q;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Question> getQuestionsBelongToCategory(String categoryTitle) {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = this.connect();
        Statement stmt = conn.createStatement();){
            String sql = "SELECT questionName FROM QUESTION,CATEGORY WHERE catTitle = '" + categoryTitle
                    + "' AND CATEGORY.catID = QUESTION.catID";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Question q = getQuestion(rs.getString(1));
                questions.add(q);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }

    public List<Category> getAllCategories() {
        List<Category> cats = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();){
            String sql = "SELECT catID, catTitle FROM CATEGORY";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cats.add(new Category(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cats;
    }

    public List<Category> getSubCategoriesOf(String categoryTitle) {
        String sql = "SELECT subCatID,sub.catTitle FROM SUBCAT,CATEGORY parent,CATEGORY sub WHERE SUBCAT.catID = parent.catID " +
                "AND parent.catTitle = ? AND sub.catID = subCatID";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1, categoryTitle);
            ResultSet rs = pstmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category cat = new Category(rs.getString(1), rs.getString(2));
                categories.add(cat);
            }
            return categories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void createNewQuiz(Quiz quiz) throws Exception{
        String sql = "INSERT INTO QUIZ(quizName, quizDescription, timeLimit, showDescription) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1, quiz.getQuizName());
            pstmt.setString(2, quiz.getQuizDescription());
            pstmt.setInt(3, quiz.getTimeLimit());
            pstmt.setInt(4,quiz.isShowDescription() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (Exception e) {
            if (e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]") && e.getMessage().contains("QUIZ.quizName")) {
                throw new NameDuplicateException("Quiz name duplicate");
            }
        }
    }

    public void addQuestionToQuiz(String quizName, String questionName, Connection conn) throws Exception{
        boolean check = false;
        if (conn == null) {
            conn = this.connect();
            check = true;
        }
        String sql = "INSERT INTO QUIZ_QUESTION(quizID, questionName) VALUES((SELECT quizID FROM QUIZ WHERE quizName = ?),?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1, quizName);
            pstmt.setString(2, questionName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
        if (check) conn.close();
    }

    public List<Question> getQuestionBelongToQuiz(String quizName) {
        String sql = "SELECT questionName FROM QUIZ, QUIZ_QUESTION WHERE quizName = ? AND QUIZ.quizID = QUIZ_QUESTION.quizID";
        List<Question> questions = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1, quizName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                questions.add(getQuestion(rs.getString(1)));
            }
            return questions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteQuestion(String questionName, Connection conn) throws Exception {
        boolean check = false;
        if (conn == null) {
            conn = this.connect();
            check = true;
        }
        String sql = "DELETE FROM OPTIONS WHERE questionName = '" + questionName + "'";
        try (Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(sql);

            sql = "DELETE FROM QUIZ_QUESTION WHERE questionName = '" + questionName + "'";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM QUESTION WHERE questionName = '" + questionName + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
        if (check) conn.close();
    }

    public void deleteCategory(String categoryTitle, Connection conn) throws Exception {
        boolean check = false;
        if (conn == null) {
            conn = this.connect();
            check = true;
        }
        String sql = "SELECT catID FROM CATEGORY WHERE catTitle = '" + categoryTitle + "'";
        String sql2 = "DELETE FROM SUBCAT WHERE subCatID = ?";
        try (Statement stmt = conn.createStatement();
             Statement stmt1 = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql2);){

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String sql1 = "SELECT catTitle, subCatID FROM CATEGORY, SUBCAT WHERE SUBCAT.catID = '"
                        + rs.getString(1) + "' AND SUBCAT.subCatID = CATEGORY.catID";
                ResultSet subRs = stmt1.executeQuery(sql1);

                while (subRs.next()) {
                    pstmt.setString(1,subRs.getString(2));
                    pstmt.executeUpdate();
                    deleteCategory(subRs.getString(1),conn);
                }

                sql = "SELECT questionName FROM QUESTION WHERE catID = '" + rs.getString(1) + "'";
                subRs = stmt1.executeQuery(sql);
                while (subRs.next()) {
                    deleteQuestion(subRs.getString(1),conn);
                }


            }

            sql = "DELETE FROM CATEGORY WHERE catTitle = '" + categoryTitle + "'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
        if (check) conn.close();
    }

    public void deleteQuiz(String quizName) {
        String sql = "DELETE FROM QUIZ_QUESTION WHERE quizID IN (SELECT quizID FROM QUIZ WHERE quizName = ?)";
        String sql2 = "DELETE FROM QUIZ WHERE quizName = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);){

            pstmt.setString(1, quizName);
            pstmt.executeUpdate();

            pstmt2.setString(1, quizName);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT quizName, quizDescription, timeLimit, shuffle, showDescription FROM QUIZ";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();){

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizName(rs.getString(1));
                quiz.setQuizDescription(rs.getString(2));
                quiz.setTimeLimit(rs.getInt(3));
                quiz.setShuffle((rs.getInt(4) == 1));
                quiz.setShowDescription(rs.getInt(5) == 1);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }

    public List<Category> getAllNonSubCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT catID, catTitle FROM CATEGORY WHERE catID NOT IN (SELECT subCatID catID FROM SUBCAT)";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();){

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categories.add(new Category(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public void editQuestion(String questionName, Question newQuestion) throws Exception {
        String sql = "SELECT catTitle FROM QUESTION,CATEGORY WHERE QUESTION.catID = CATEGORY.catID AND questionName = ?";
        String sql2 = "SELECT quizName FROM QUIZ_QUESTION, QUIZ WHERE QUIZ_QUESTION.quizID = QUIZ.quizID AND questionName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);) {

            pstmt.setString(1, questionName);
            ResultSet catRs = pstmt.executeQuery();
            catRs.next();
            String catTitle = catRs.getString(1);

            pstmt2.setString(1, questionName);
            ResultSet quizRs = pstmt2.executeQuery();
            List<String> quizNames = new ArrayList<>();
            while (quizRs.next()) {
                quizNames.add(quizRs.getString(1));
            }

            deleteQuestion(questionName,conn);

            insertQuestion(newQuestion, catTitle, conn);

            for (String quizName : quizNames) {
                addQuestionToQuiz(quizName, newQuestion.getQuestionName(),conn);
            }
        }
    }

    public void removeQuestionFromQuiz(String quizName, String questionName) throws Exception{
        String sql = "DELETE FROM QUIZ_QUESTION WHERE quizID IN (SELECT quizID FROM QUIZ WHERE quizName = ?) " +
                "AND questionName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, quizName);
            pstmt.setString(2, questionName);
            pstmt.executeUpdate();
        }
    }

    public void setShuffle(String quizName, boolean isShuffle) throws Exception{
        String sql = "UPDATE QUIZ SET shuffle = ? WHERE quizName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,isShuffle ? 1 : 0);
            pstmt.setString(2,quizName);
            pstmt.executeUpdate();
        }
    }

    public void treeView(String categoryTitle, int level) {
        for (int i = 0; i < level; i++) System.out.print(' ');
        System.out.println(categoryTitle);
        List<Category> subCategories = getSubCategoriesOf(categoryTitle);
        for (Category cat : subCategories) {
            treeView(cat.getCategoryTitle(), level + 1);
        }
    }
}
