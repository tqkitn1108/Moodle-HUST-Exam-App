package start;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Category;
import model.DBInteract;

import java.util.List;

public class TEST6 extends Application {

    DBInteract dbInteract = new DBInteract();
    ComboBox<String> comboBox = new ComboBox<String>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Khởi tạo ComboBox và danh sách các mục
        for (Category category : dbInteract.getAllNonSubCategories()) {
            backtracking(category, 0);
        }
        // Hiển thị ComboBox và TreeView trên giao diện người dùng
        VBox root = new VBox(10);
        root.getChildren().add(comboBox);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void backtracking(Category category, int order) {
        comboBox.getItems().add("  ".repeat(order) + category.getCategoryTitle());
        List<Category> subCategories = dbInteract.getSubCategoriesOf(category.getCategoryTitle());
        if (subCategories != null) {
            for (Category subCategory : subCategories) {
                backtracking(subCategory, order + 1);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}