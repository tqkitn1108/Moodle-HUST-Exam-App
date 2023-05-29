package start;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TEST5 extends Application {

    private MFXScrollPane scrollPane;
    private VBox questionBox;
    private Button[] questionButtons;
    private GridPane questionGridPane;
    private ToggleGroup[] answerGroups = new ToggleGroup[10];

    @Override
    public void start(Stage primaryStage) {
        // Initialize scroll pane and question box
        scrollPane = new MFXScrollPane();
        questionBox = new VBox(10);
        questionBox.setPadding(new Insets(10));
        for (int i = 1; i <= 10; i++) {
            Label questionLabel = new Label("Question " + i + ": What is the answer?");
            questionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            VBox answerBox = new VBox(5);
            answerBox.setPadding(new Insets(10));
            answerGroups[i - 1] = new ToggleGroup();
            for (int j = 1; j <= 4; j++) {
                RadioButton answerButton = new RadioButton("Answer " + j);
                answerButton.setToggleGroup(answerGroups[i - 1]);
                answerBox.getChildren().add(answerButton);
            }
            questionBox.getChildren().addAll(questionLabel, answerBox);
        }
        scrollPane.setContent(questionBox);

        // Initialize question buttons
        questionButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            questionButtons[i] = new Button("Question " + (i + 1));
            int finalI = i;
            questionButtons[i].setOnAction(event -> {
                double scrollToY = questionBox.getChildren().get(finalI * 2).getLayoutY();
                scrollPane.setVvalue(scrollToY / questionBox.getHeight());
            });
        }

        // Initialize question grid pane
        questionGridPane = new GridPane();
        questionGridPane.setAlignment(Pos.CENTER);
        questionGridPane.setPadding(new Insets(10));
        questionGridPane.setHgap(10);
        questionGridPane.setVgap(10);
        for (int i = 0; i < 5; i++) {
            questionGridPane.add(questionButtons[i], 0, i);
            questionGridPane.add(questionButtons[i + 5], 1, i);
        }

        // Initialize border pane
        BorderPane root = new BorderPane();
        root.setLeft(scrollPane);
        root.setRight(questionGridPane);

        // Create scene and show stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}