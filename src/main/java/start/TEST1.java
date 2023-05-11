package start;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TEST1 extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Giao diện ban đầu
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 400);

        // Button
        Button button = new Button("Click me!");
        button.setOnAction(e -> {
            // Hiển thị cửa sổ xác nhận
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to change the interface?");

            // Nếu xác nhận, thay đổi giao diện
            if (alert.showAndWait().get() == ButtonType.OK) {
                root.getChildren().clear();
                Label label = new Label("Hello World!");
                root.getChildren().add(label);
            }
        });
        root.getChildren().add(button);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}