package start;

import com.itextpdf.kernel.pdf.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TEST3 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Image image = new Image("C:\\Users\\KIEN\\Pictures\\Screenshots\\Screenshot 2023-06-27 162043.png");
        ImageView imageView = new ImageView(image);

        if (image.getHeight() > 300) {
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);
        }
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
