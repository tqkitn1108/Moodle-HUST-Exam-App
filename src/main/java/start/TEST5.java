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

public class TEST5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Tạo một TextArea để nhập và chèn văn bản và ảnh
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(200);
        textArea.setEditable(false);

        // Tạo một Label để hiển thị nội dung văn bản và ảnh
        Label label = new Label();
        label.setPrefHeight(200);
        label.setStyle("-fx-border-color: red");

        // Tạo một ImageView để hiển thị ảnh
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        // Tạo một VBox để chứa các thành phần giao diện
        VBox vbox = new VBox(textArea, label, imageView);

        // Thiết lập sự kiện khi người dùng nhập văn bản vào TextArea
//        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
//            // Hiển thị nội dung văn bản và ảnh trên Label
//            label.setText(newValue);
//        });

        // Thiết lập sự kiện khi người dùng chèn ảnh vào TextArea
//        textArea.setOnKeyTyped(event -> {
//            if (event.isControlDown() && event.getCharacter().equals("g")) {
//                // Hiển thị hộp thoại chọn ảnh
//                Image image = new Image("path/to/image.png");
//                imageView.setImage(image);
//            }
//        });

        textArea.setOnKeyPressed(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if (event.isShortcutDown() && event.getCode() == KeyCode.V) {
                if (clipboard.hasImage()) {
                    System.out.println("hahaha");
                    Image image = clipboard.getImage();
                    imageView.setImage(image);
//                    label.setGraphic(imageView);
                    event.consume();
                }
            }
        });

        // Tạo một Scene để hiển thị VBox
        Scene scene = new Scene(vbox);

        // Đặt Scene vào Stage và hiển thị Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
