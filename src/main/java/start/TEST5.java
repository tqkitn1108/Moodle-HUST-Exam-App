package start;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Category;
import model.DBInteract;
import model.Question;
import model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class TEST5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBInteract dbInteract = new DBInteract();
        List<Quiz> quizzes = dbInteract.getAllQuizzes();
        for(Quiz quiz : quizzes){
            for(Question question: quiz.getQuestions()){
                question.showQ();
            }
            System.out.println(quiz.getTimeLimit());
        }
//        List<Category> categories = dbInteract.getAllCategories();
//        for(Category category : categories){
//            System.out.println(category.getCatTitle());
//            for(Question question : category.getQuestions()){
//                question.showQ();
//            }
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
