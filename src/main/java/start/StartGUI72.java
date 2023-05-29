package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartGUI72 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Kien_FXML/GUI72.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Hệ thống ôn thi trắc nghiệm");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}