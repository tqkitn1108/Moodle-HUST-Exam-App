package start;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST3 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tạo VBox và các HBox, label, button tương ứng
        VBox vBox = new VBox();
        HBox[] hBoxes = new HBox[5];
        Label[] labels = new Label[5];
        Button[] buttons = new Button[5];
        for (int i = 0; i < 5; i++) {
            hBoxes[i] = new HBox();
            labels[i] = new Label("Label " + i);
            buttons[i] = new Button("Button " + i);
            hBoxes[i].getChildren().addAll(labels[i], buttons[i]);
            vBox.getChildren().add(hBoxes[i]);
        }

        // Thêm sự kiện cho các button
        for (int i = 0; i < 5; i++) {
            int index = i; // Biến index cần được khai báo final hoặc "effectively final" để sử dụng trong sự kiện
            buttons[i].setOnAction(event -> {
//                labels[index].setText("Hello");
                Node button = (Button) event.getSource();
                HBox hbox = (HBox) button.getParent();
                Label label = (Label) hbox.lookup(".label");
                label.setText("Hello");
            });
        }

        // Tạo Scene và hiển thị cửa sổ
        Scene scene = new Scene(vBox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}