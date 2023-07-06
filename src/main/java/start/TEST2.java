package start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TEST2 extends Application {

    private Label label1, label2, label3;
    private ComboBox<String> comboBox;
    private StackPane stackPane1, stackPane2, stackPane3;
    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) {
        // Tạo các label với nội dung và màu sắc khác nhau
        label1 = new Label("Giao diện 1");
        label1.setTextFill(Color.WHITE);
        label1.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        label1.setPadding(new Insets(10));
        stackPane1 = new StackPane(label1);
        stackPane1.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        label2 = new Label("Giao diện 2");
        label2.setTextFill(Color.WHITE);
        label2.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        label2.setPadding(new Insets(10));
        stackPane2 = new StackPane(label2);
        stackPane2.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        label3 = new Label("Giao diện 3");
        label3.setTextFill(Color.WHITE);
        label3.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        label3.setPadding(new Insets(10));
        stackPane3 = new StackPane(label3);
        stackPane3.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        // Tạo comboBox với các lựa chọn là tên các giao diện
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Giao diện 1", "Giao diện 2", "Giao diện 3");
        comboBox.setOnAction(event -> showSelectedPane());

        // Tạo BorderPane để chứa các giao diện và comboBox
        borderPane = new BorderPane();
        borderPane.setCenter(stackPane1);
        borderPane.setBottom(comboBox);
        BorderPane.setAlignment(comboBox, Pos.CENTER);
        borderPane.setPadding(new Insets(10));

        // Tạo Scene và hiển thị
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Hiển thị giao diện được chọn từ comboBox
    private void showSelectedPane() {
        if (comboBox.getValue().equals("Giao diện 1")) {
            borderPane.setCenter(stackPane1);
        } else if (comboBox.getValue().equals("Giao diện 2")) {
            borderPane.setCenter(stackPane2);
        } else if (comboBox.getValue().equals("Giao diện 3")) {
            borderPane.setCenter(stackPane3);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
