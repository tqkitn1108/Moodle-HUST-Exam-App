package start;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.IOException;

public class Itext7PdfTest {
    public static void main(String[] args) throws IOException {

        String imgSrc = "D:\\javaFX\\BTL_OOP_20222\\src\\main\\resources\\img\\app-gallery.png";
        ImageData data = ImageDataFactory.create(imgSrc);
        Image image1 = new Image(data);
        String path = "example.pdf";
        String paraText = "Chương trình phải thực hiện được việc hiển thị giao diện Popup 1.2 trong giao diện 1.1: nhấn vào " +
                "nút cài đặt Viết một chương trình C nhập vào 3 số nguyên. Thiết lập một con trỏ để lần lượt trỏ tới từng số " +
                "nguyên và hiển thị kết quả giá trị tham chiếu ngược của con trỏ.\n" +
                "Lưu ý: Phép toán & trả về địa chỉ của biến.\n";
        List list1 = new List();
        list1.add("Hahaha1");
        list1.add("Hahaha2");
        list1.add("Hahaha3");
        list1.add("Hahaha4");
        list1.add("Hahaha5");
        Paragraph paragraph1 = new Paragraph(paraText);
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);
        PdfFont pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        Text text1 = new Text("You are very").setFont(pdfFont);
        Text text2 = new Text("beautiful").setFont(boldFont);

        Paragraph paragraph = new Paragraph()
                .add(text1)
                        .add(text2);

        document.add(paragraph1);
        document.add(image1);
        document.add(list1);
        document.add(paragraph);

        document.close();

        System.out.println("PDF created successfully");
    }
}