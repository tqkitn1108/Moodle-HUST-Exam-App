package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmationDialogExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tạo button
        Button button = new Button("Xác nhận");

        button.setOnAction(event -> {
            // Tạo cửa sổ xác nhận tùy chỉnh
            Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);

            VBox layout = new VBox();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/GUI72.fxml"));
            try {
                Node node = fxmlLoader.load();
                layout.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(layout);
            dialog.setScene(scene);
            dialog.show();
        });

        // Tạo layout
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.getChildren().add(button);

        // Tạo scene
        Scene scene = new Scene(root, 600, 500);

        // Thiết lập stage
        primaryStage.setTitle("Custom Confirmation Dialog Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}