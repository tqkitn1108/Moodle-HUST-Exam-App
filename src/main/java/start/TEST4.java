package start;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import controller.Kien_Controller.QuestionLayoutController;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TEST4 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        anchorPane.getChildren().add(scrollPane);
        // Tạo một đối tượng VBox và đặt nó làm nội dung của Scene
        VBox vbox = new VBox(20);

        // Đặt màu nền của VBox là màu xanh lam
        BackgroundFill backgroundFill = new BackgroundFill(Color.DEEPSKYBLUE, null, null);
        Background background = new Background(backgroundFill);
        vbox.setBackground(background);

        // Thêm các node vào VBox
        for (int i = 0; i < 30; ++i) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            Node node = fxmlLoader.load();
            QuestionLayoutController questionLayoutController = fxmlLoader.getController();
            questionLayoutController.setQuestionNum(i);
            vbox.getChildren().add(node);
        }

        // Thêm ScrollPane vào VBox
        scrollPane.setContent(vbox);
        scrollPane.fitToHeightProperty();
        scrollPane.fitToWidthProperty();

        // Thêm một ảnh vào VBox
        StackPane stackPane = new StackPane();
        javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream("/img/arrow.png"));
        ImageView arrowImg = new ImageView();
        arrowImg.setImage(image);
        stackPane.getChildren().add(arrowImg);
        vbox.getChildren().add(stackPane);

        // Đặt Scene và hiển thị nó
        Scene scene = new Scene(anchorPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Tạo một tài liệu PDF và ghi giao diện vào nó
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream("example.pdf"));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);
            document.setMargins(50, 50, 50, 50);

//            // Chụp hình ảnh của giao diện JavaFX
//            WritableImage fxImage = vbox.snapshot(null, null);
//
//            // Tạo một đối tượng Image mới từ hình ảnh vừa chụp
//            Image pdfImage = new Image(ImageDataFactory.create(SwingFXUtils.fromFXImage(fxImage, null), null));

            // Thêm hình ảnh và chữ ký vào tài liệu PDF
//            document.add(new Paragraph("Đề thi trắc nghiệm môn OOP ").setBold().setFontSize(18).setMarginBottom(30));
//            document.add(pdfImage);

            // Kiểm tra chiều cao của VBox và chiều cao còn lại của trang PDF
            // Tính toán chiều cao của VBox và vị trí bắt đầu bạn muốn chụp
            double vboxHeight = vbox.getHeight();
            double pageHeight = document.getPdfDocument().getDefaultPageSize().getHeight();
            double startY = 0;
            // Nếu VBox có độ cao lớn hơn vị trí bắt đầu bạn muốn chụp
            while (vboxHeight > startY) {
                // Tính toán kích thước của phần VBox bạn muốn chụp
                double partHeight = vboxHeight - startY;
                double partWidth = vbox.getWidth();
                System.out.println(partHeight);
                System.out.println(startY);
                System.out.println(partWidth);

                // Tạo một đối tượng Rectangle để chỉ định phần của VBox bạn muốn chụp
                Rectangle2D partRect = new Rectangle2D(0, startY, partWidth, partHeight);

                SnapshotParameters params = new SnapshotParameters();
                params.setViewport(partRect);

                // Chuyển phần của VBox sang hình ảnh và chụp vào tài liệu PDF
                WritableImage partImage = vbox.snapshot(params, null);
                Image partPdfImage = new Image(ImageDataFactory.create(SwingFXUtils.fromFXImage(partImage, null), null));
//                document.add(new AreaBreak());
                document.add(partPdfImage);

                startY+=1380;
            }

            // Đóng tài liệu PDF
            document.close();

            System.out.println("Tài liệu PDF đã được tạo thành công.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
