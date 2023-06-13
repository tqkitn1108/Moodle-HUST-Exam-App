package start;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TEST1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Tạo một danh sách chứa 100 câu hỏi
        List<String> questionList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            questionList.add("Question " + i);
        }

// Tạo một ListView để hiển thị danh sách câu hỏi
        ListView<String> listView = new ListView<>();

// Thiết lập số lượng câu hỏi hiển thị trên mỗi trang là 10
        int itemsPerPage = 10;

// Tạo một phân trang để chia danh sách thành các trang
        Pagination pagination = new Pagination((questionList.size() / itemsPerPage) + 1, 0);
        pagination.setPageFactory(pageIndex -> {
            // Tạo một ObservableList để chứa các câu hỏi trên trang
            ObservableList<String> items = FXCollections.observableArrayList();

            // Lấy ra vị trí đầu tiên và cuối cùng của câu hỏi trên trang hiện tại
            int startIndex = pageIndex * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, questionList.size());

            // Thêm các câu hỏi vào ObservableList
            for (int i = startIndex; i < endIndex; i++) {
                items.add(questionList.get(i));
            }

            // Thiết lập dữ liệu cho ListView
            listView.setItems(items);

            // Trả về ListView
            return listView;
        });

// Thêm Pagination vào giao diện
// Ví dụ: Thêm vào VBox với tên là "root"
        VBox root = new VBox();
        root.getChildren().add(pagination);
        root.setPrefHeight(Region.USE_COMPUTED_SIZE);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}