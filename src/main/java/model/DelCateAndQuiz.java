package model;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Category;
import model.DBInteract;
import model.Question;
import model.Quiz;

import java.util.List;

public class DelCateAndQuiz extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBInteract dbInteract = new DBInteract();
//        List<Quiz> quizzes = dbInteract.getAllQuizzes();
//        for(Quiz quiz : quizzes){
//////            for(Question question: quiz.getQuestions()){
//////                question.showQ();
//////            }
////        }
        List<Category> categories = dbInteract.getAllCategories();
        for(Category category : categories){
            if(category.getCategoryTitle().equals("Test SubCategory")){
                System.out.println(category.getCategoryTitle());
                dbInteract.deleteCategory(category.getCategoryTitle(),null);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
