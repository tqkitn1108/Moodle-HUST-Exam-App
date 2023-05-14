package start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TEST3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo button đầu tiên
        Button redButton = new Button("Click me");
        redButton.setOnAction(e -> {
            // Tạo dialog xác nhận
            Dialog<ButtonType> confirmDialog = new Dialog<>();
            confirmDialog.setTitle("Confirmation");
            AnchorPane confirmPane = new AnchorPane();
            confirmPane.setPrefSize(300, 100);
            confirmPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

            // Thiết lập các nút cho dialog
            Button okButton = new Button("OK");
            okButton.setOnAction(event -> {
                // Tạo giao diện mới với màu nền xanh lam
                BorderPane bluePane = new BorderPane();
                bluePane.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, null, null)));
                Scene blueScene = new Scene(bluePane, 400, 400);
                primaryStage.setScene(blueScene);
                Stage dialogStage = (Stage) confirmDialog.getDialogPane().getScene().getWindow();
                dialogStage.close();
            });
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(event -> confirmDialog.close());
            AnchorPane.setRightAnchor(okButton, 10.0);
            AnchorPane.setBottomAnchor(okButton, 10.0);
            AnchorPane.setRightAnchor(cancelButton, 80.0);
            AnchorPane.setBottomAnchor(cancelButton, 10.0);
            confirmPane.getChildren().addAll(okButton, cancelButton);
            confirmDialog.getDialogPane().setContent(confirmPane);

            // Hiển thị dialog và xử lý kết quả
            confirmDialog.showAndWait();
        });

        // Tạo giao diện đầu tiên với màu nền đỏ
        BorderPane redPane = new BorderPane();
        redPane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        redPane.setCenter(new StackPane(redButton));
        redPane.setPadding(new Insets(10));
        Scene redScene = new Scene(redPane, 400, 400);

        // Thiết lập giao diện đầu tiên và hiển thị
        primaryStage.setScene(redScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}