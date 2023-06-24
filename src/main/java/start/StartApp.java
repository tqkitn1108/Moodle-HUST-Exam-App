package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class StartApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HA_FXML/GUI11.fxml")));
        Stage stage = new Stage();
        stage.setTitle("PHẦN MỀM TẠO NGÂN HÀNG ĐỀ VÀ TỔ CHỨC THI TRẮC NGHIỆM");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
