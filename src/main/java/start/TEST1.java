package start;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo một label để chứa hình ảnh
        Label label = new Label();
        Image img = new Image(getClass().getResourceAsStream("/img/arrow.png"));
        ImageView imageView = new ImageView(img);
        label.setGraphic(imageView);

        // Tạo một TextField để chứa hình ảnh
//        TextField textField = new TextField();

        // Bắt sự kiện phím tắt Ctrl+V để dán hình ảnh từ clipboard vào label hoặc text field
        label.setFocusTraversable(true);
        label.setOnKeyPressed(event -> {
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

        VBox root = new VBox(10, label);
        Scene scene = new Scene(root, 300, 200);
        scene.setOnKeyReleased(event -> {
            if (event.isShortcutDown() && event.getCode() == KeyCode.V) {
                System.out.println("Ctrl+V pressed");
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}