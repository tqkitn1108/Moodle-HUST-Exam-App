package start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Giao dien chinh
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Button button = new Button("Click me!");
        button.setOnAction(e -> {
            // Hien thi popup
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Option 1", "Option 1", "Option 2", "Option 3");
            dialog.setTitle("Select option");
            dialog.setHeaderText("Choose your path");
            String choice = dialog.showAndWait().get();

            if (choice.equals("Option 1")) {
                // Mo giao dien Option 1
                stage.setScene(new Scene(new VBox(20), 300, 200));
            } else if (choice.equals("Option 2")) {
                // Mo giao dien Option 2
                stage.setScene(new Scene(new VBox(10, new Button("Option 2")), 500, 300));
            } else {
                // Mo giao dien Option 3
                stage.setScene(new Scene(new VBox(10, new Button("Option 3")), 700, 400));
            }
        });

        root.getChildren().add(button);
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }
}