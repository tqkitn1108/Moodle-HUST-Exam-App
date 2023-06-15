package start;

import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TEST3 extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //Tạo đối tượng Document
        Document document = new Document(new PdfDocument(new PdfWriter("mypdf.pdf",
                new WriterProperties().setStandardEncryption("userPassword".getBytes(),
                        "ownerPassword".getBytes(),
                        EncryptionConstants.ALLOW_PRINTING,
                        EncryptionConstants.ENCRYPTION_AES_128 | EncryptionConstants.DO_NOT_ENCRYPT_METADATA))));

        //Mở Document
        document.add(new Paragraph("Nội dung của file PDF"));

        //Đóng Document
        document.close();

        System.out.println("Tạo file PDF thành công!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}