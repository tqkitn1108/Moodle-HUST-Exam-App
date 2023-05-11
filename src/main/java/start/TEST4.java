package start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST4 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tạo một VBox layout và thiết lập padding
        VBox root = new VBox();
        root.setPadding(new Insets(10));

        // Tạo một nút để hiển thị menu
        Button button = new Button("Chọn giao diện");
        root.getChildren().add(button);

        // Tạo một menu bar với các menu items tương ứng với các giao diện
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("Giao diện 1");
        Menu menu2 = new Menu("Giao diện 2");
        MenuItem menuItem1 = new MenuItem("Giao diện 1.1");
        MenuItem menuItem2 = new MenuItem("Giao diện 1.2");
        MenuItem menuItem3 = new MenuItem("Giao diện 2.1");
        MenuItem menuItem4 = new MenuItem("Giao diện 2.2");
        menu1.getItems().addAll(menuItem1, menuItem2);
        menu2.getItems().addAll(menuItem3, menuItem4);
        menuBar.getMenus().addAll(menu1, menu2);

        // Xử lý sự kiện click cho các menu items
        menuItem1.setOnAction(event -> {
            // Hiển thị giao diện 1.1
            // ...
        });
        menuItem2.setOnAction(event -> {
            // Hiển thị giao diện 1.2
            // ...
        });
        menuItem3.setOnAction(event -> {
            // Hiển thị giao diện 2.1
            // ...
        });
        menuItem4.setOnAction(event -> {
            // Hiển thị giao diện 2.2
            // ...
        });

        // Tạo một scene và hiển thị nó
        root.getChildren().add(menuBar);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
