//package start;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import com.itextpdf.io.font.constants.StandardFonts;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfPage;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
//import com.itextpdf.layout.ColumnDocumentRenderer;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//public class PDFTEST2 extends Application {
//
//    private static final int QUESTION_COUNT = 10;
//
//    VBox vbox;
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("PDF Generator");
//
//        // Create a VBox container to hold the ScrollPane and Button
//        vbox = new VBox();
//        vbox.setSpacing(10);
//        vbox.setPadding(new Insets(10, 10, 10, 10));
//
//        // Create a ScrollPane to hold the questions
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setPrefSize(400, 400);
//
//        // Create a GridPane to hold the questions
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.setPadding(new Insets(10, 10, 10, 10));
//
//        // Add questions to the GridPane
//        for (int i = 1; i <= QUESTION_COUNT; i++) {
//            Label questionLabel = new Label("Question " + i + ":");
//            questionLabel.setFont(Font.font("Arial", 14));
//            TextArea questionTextArea = new TextArea();
//            questionTextArea.setPrefRowCount(3);
//            questionTextArea.setWrapText(true);
//            questionTextArea.setText("Quang Kiên Tạ ahahahahahahah");
//            gridPane.addRow(i, questionLabel, questionTextArea);
//        }
//
//        // Add the GridPane to the ScrollPane
//        scrollPane.setContent(gridPane);
//
//        // Add the ScrollPane and Button to the VBox
//        vbox.getChildren().addAll(scrollPane, createButton(primaryStage));
//
//        // Create a Scene and add the VBox to it
//        Scene scene = new Scene(vbox, 400, 500);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private Button createButton(Stage stage) {
//        Button button = new Button("Export to PDF");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                // Create a FileChooser to choose where to save the PDF
//                FileChooser fileChooser = new FileChooser();
//                fileChooser.setTitle("Save PDF");
//                fileChooser.setInitialFileName("test.pdf");
//                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
//                File file = fileChooser.showSaveDialog(stage);
//
//                if (file != null) {
//                    // Create a PdfDocument and add a PdfPage to it
//                    PdfWriter pdfWriter = null;
//                    try {
//                        pdfWriter = new PdfWriter(file.getName());
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//                    PdfPage page = pdfDocument.addNewPage();
//                    Document document = new Document(pdfDocument);
//
//                    // Create a PdfCanvas and write the ScrollPane content to it
//                    PdfCanvas canvas = new PdfCanvas(page);
//                    Text text = new Text(scrollPaneToString());
//                    Paragraph paragraph = new Paragraph(text.getText());
//                    paragraph.setRelativePosition(10, 100, 10, 10);
//
//                    PdfFont font = null;
//                    try {
//                        font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    float fontSize = 12;
//                    float y = page.getPageSize().getTop() - 50;
//
//                    canvas.beginText()
//                            .moveText(50, y)
//                            .setFontAndSize(font, fontSize)
//                            .showText(text.getText())
//                            .endText()
//                            .release();
//                    // Save the document to the specified file
//                    document.add(paragraph);
//                    document.close();
//                }
//            }
//        });
//        return button;
//    }
//
//    private String scrollPaneToString() {
//        StringBuilder sb = new StringBuilder();
//        GridPane gridPane = (GridPane) ((ScrollPane) vbox.getChildren().get(0)).getContent();
//        for (int i = 1; i <= QUESTION_COUNT; i++) {
//            Label questionLabel = (Label) gridPane.getChildren().get(i * 2 - 2);
//            TextArea questionTextArea = (TextArea) gridPane.getChildren().get(i * 2 - 1);
//            sb.append(questionLabel.getText()).append("\n").append(questionTextArea.getText()).append("\n\n");
//        }
//        return sb.toString();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
