package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class StartApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Ha_FXML/Main2Screen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Hệ thống ôn thi trắc nghiệm");
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
