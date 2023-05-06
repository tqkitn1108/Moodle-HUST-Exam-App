package start;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TEST2 extends Application {

    private ImageView imageView;
    private Label label;

    public void start(Stage primaryStage) {

        // Tạo một đối tượng Label và đặt text cho label đó
        label = new Label("This is a label");
        label.setPadding(new Insets(5)); // Đặt padding cho label
        label.setMinHeight(30); // Đặt chiều cao tối thiểu cho label

        // Tạo một nút để import file ảnh
        Button button = new Button("Import Image");
        button.setOnAction(event -> {
            // Hiển thị hộp thoại chọn file để import
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                // Nếu import được ảnh, thì hiển thị ảnh với kích thước 50x50
                Image image = new Image(file.toURI().toString());
                imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                label.setGraphic(imageView);
                label.setMinHeight(55);
            } else {
                // Nếu không import được ảnh, thì đặt chiều cao tối thiểu của label là 30
                label.setGraphic(null);
                label.setMinHeight(30);
            }
        });

        // Tạo một layout VBox để chứa label và nút
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(label, button);

        // Tạo một Scene với layout VBox và đặt kích thước của cửa sổ
        Scene scene = new Scene(root, 300, 200);

        // Đặt tiêu đề cho cửa sổ và hiển thị cửa sổ
        primaryStage.setTitle("Label with Image from File Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}