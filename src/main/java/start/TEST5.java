package start;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Category;
import model.DBInteract;
import model.Question;
import model.Quiz;

import java.util.List;

public class TEST5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBInteract dbInteract = new DBInteract();
        List<Quiz> quizzes = dbInteract.getAllQuizzes();
//        dbInteract.deleteQuiz("Đây là quiz test nhé");
//        for(Quiz quiz : quizzes){
////            for(Question question: quiz.getQuestions()){
////                question.showQ();
////            }
//            System.out.println(quiz.getQuizName());
//        }
        List<Category> categories = dbInteract.getAllCategories();
        for(Category category : categories){
            for(Question question : category.getQuestions()){
//                question.showQ();
                if(question.getQuestionName().length() == 0) {
                    dbInteract.deleteQuestion(question.getQuestionName(), null);
                    System.out.println("hha");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
