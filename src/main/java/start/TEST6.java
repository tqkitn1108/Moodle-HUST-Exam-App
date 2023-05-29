//package start;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.util.Optional;
//
//import com.itextpdf.io.image.ImageDataFactory;
//import com.itextpdf.kernel.geom.PageSize;
//import com.itextpdf.kernel.pdf.*;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Image;
//import com.itextpdf.layout.element.Paragraph;
//
//import javafx.application.Application;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.Scene;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.WritableImage;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//public class TEST6 extends Application {
//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//        // Tạo một đối tượng VBox và đặt nó làm nội dung của Scene
//        VBox vbox = new VBox(20);
//
//        // Đặt màu nền của VBox là màu xanh lam
//        BackgroundFill backgroundFill = new BackgroundFill(Color.DEEPSKYBLUE, null, null);
//        Background background = new Background(backgroundFill);
//        vbox.setBackground(background);
//
//        // Thêm một đoạn văn bản và một ToggleGroup với 4 RadioButton vào VBox
//        Text text = new Text("Câu hỏi: Ai là người đầu tiên đặt chân lên mặt trăng?");
//        RadioButton radioButton1 = new RadioButton("Neil Armstrong");
//        RadioButton radioButton2 = new RadioButton("Buzz Aldrin");
//        RadioButton radioButton3 = new RadioButton("Michael Collins");
//        RadioButton radioButton4 = new RadioButton("Yuri Gagarin");
//        ToggleGroup toggleGroup = new ToggleGroup();
//        radioButton1.setToggleGroup(toggleGroup);
//        radioButton2.setToggleGroup(toggleGroup);
//        radioButton3.setToggleGroup(toggleGroup);
//        radioButton4.setToggleGroup(toggleGroup);
//        vbox.getChildren().addAll(text, radioButton1, radioButton2, radioButton3, radioButton4);
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(vbox);
//
//        // Thêm một ảnh vào VBox
//        StackPane stackPane = new StackPane();
//        WritableImage image = new WritableImage(200, 200);
//        stackPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
//        stackPane.getChildren().add(new ImageView(image));
//        vbox.getChildren().add(stackPane);
//
//        // Đặt Scene và hiển thị nó
//        Scene scene = new Scene(vbox, 1000, 600);
//        stage.setScene(scene);
//        stage.show();
//
//        // Tạo một tài liệu PDF và ghi giao diện vào nó
//        try {
//            PdfWriter writer = new PdfWriter(new FileOutputStream("example.pdf"));
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            Document document = new Document(pdfDocument, PageSize.A4);
//            document.setMargins(50, 50, 50, 50);
//
//            // Chụp hình ảnh của giao diện JavaFX
//            WritableImage fxImage = vbox.snapshot(null, null);
//
//
//            // Tạo một đối tượng Image mới từ hình ảnh vừa chụp
//            Image pdfImage = new Image(ImageDataFactory.create(SwingFXUtils.fromFXImage(fxImage, null), null));
//
//            // Thêm hình ảnh và chữ ký vào tài liệu PDF
//            document.add(new Paragraph("Câu hỏi: Ai là người đầu tiên đặt chân lên mặt trăng?").setBold().setFontSize(18).setMarginBottom(30));
//            document.add(pdfImage);
//            document.close();
//
//            System.out.println("Tài liệu PDF đã được tạo thành công.");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
