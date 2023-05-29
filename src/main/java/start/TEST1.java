//package start;
//
//import com.itextpdf.io.font.constants.StandardFonts;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfPage;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
//import com.itextpdf.layout.element.Paragraph;
//import javafx.application.Application;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//public class TEST1 extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Tạo VBox và thêm các câu hỏi vào đó
//        VBox vBox = new VBox();
//        for (int i = 0; i < 10; i++) {
//            Text question = new Text("Question " + (i + 1));
//            vBox.getChildren().add(question);
//        }
//
//        // Tạo scrollPane và đặt VBox làm nội dung
//        ScrollPane scrollPane = new ScrollPane(vBox);
//
//        // Tạo button và đặt sự kiện click
//        Button button = new Button("Export to PDF");
//        button.setOnAction(event -> {
//            // Tạo tài liệu PDF
//            PdfDocument pdfDoc = null;
//            try {
//                pdfDoc = new PdfDocument(new PdfWriter("output.pdf"));
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Tạo trang PDF
//            PdfPage page = pdfDoc.addNewPage();
//
//            // Tạo Canvas để vẽ trang PDF
//            PdfCanvas canvas = new PdfCanvas(page);
//
//            // Thiết lập font cho văn bản
//            PdfFont font = null;
//            try {
//                font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            float fontSize = 12;
//            Paragraph paragraph = new Paragraph();
//
//            // Duyệt các nút con của VBox và thêm chúng vào trang PDF
//            float y = page.getPageSize().getTop() - 50;
//            for (Node node : vBox.getChildren()) {
//                if (node instanceof Text) {
//                    Text question = (Text) node;
//                    canvas.beginText()
//                            .setFontAndSize(font, fontSize)
//                            .moveText(50, y )
//                            .showText(question.getText())
//                            .endText();
//                    fontSize += 2;
//                    y-=20;
//                }
//            }
//
//            // Đóng tài liệu PDF
//            pdfDoc.close();
//        });
//
//        // Đặt scrollPane và button vào BorderPane
//        BorderPane root = new BorderPane();
//        root.setCenter(scrollPane);
//        root.setBottom(button);
//
//        // Tạo Scene và hiển thị Stage
//        Scene scene = new Scene(root, 400, 400);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}
