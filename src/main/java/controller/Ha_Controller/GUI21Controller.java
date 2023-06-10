package controller.Ha_Controller;

import com.jfoenix.controls.JFXCheckBox;
import controller.Thien_Controller.GUI32Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GUI21Controller implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private AnchorPane quesListPane;
    @FXML
    private ImageView arrowImg;
    @FXML
    private MFXButton chooseBtn;
    @FXML
    private VBox boxToDropFile;

    @FXML
    private Label fileLabel;

    private File file, file2;

    private HeaderListener headerListener;

    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    @FXML
    public void chooseAFile(ActionEvent event) {
        Stage thisStage = (Stage) chooseBtn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        file2 = fileChooser.showOpenDialog(thisStage);
        if (file2 != null) {
            file = file2;
            String fileName = file.getName();
            addFileLabel(fileName);
        }
    }

    public void addFileLabel(String fileName) {
        fileLabel.setVisible(true);
        Image img = new Image(getClass().getResourceAsStream("/img/archive.png"));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        fileLabel.setText(fileName);
        fileLabel.setGraphic(imageView);
        fileLabel.setPrefHeight(30);
        VBox.setMargin(fileLabel, new Insets(0, 0, 0, 285));
    }

    @FXML
    public void dragExited(DragEvent event) {
        boxToDropFile.setStyle("-fx-border-color:  #000; -fx-border-style: dashed;");
    }

    @FXML
    private void dragFileOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            boxToDropFile.setStyle("-fx-border-color:  #0693E3; -fx-border-style: dashed;");
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    public void dropFile(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            dragboard.getFiles().forEach(file -> {
                this.file = file;
//                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
                addFileLabel(file.getName());
            });
        }
        event.setDropCompleted(true);
        event.consume();
    }

    @FXML
    public void importFile(ActionEvent event) {
        if (file == null && file2 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No file is chosen, try again");
            alert.showAndWait();
        }
        if (file != null) {
            try {
                String fileName = file.getName();
                // Kiểm tra định dạng tệp
                if (!fileName.endsWith(".txt") && !fileName.endsWith(".docx") && !fileName.endsWith(".doc")) {
                    throw new WrongFormatException("Please choose a file with tail .txt, .doc or .docx");
                }
                // Đọc nội dung tệp và xử lý ở đây
                Scanner scanner = new Scanner(file);
//                String firstLine = scanner.nextLine();

                // Xử lý khi import thành công
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Import successfully!");
                alert.showAndWait();
            } catch (IOException e) {
                // Xử lý khi import thất bại
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Import failed, try again");
                alert.showAndWait();
            } catch (WrongFormatException e) {
                // Xử lý khi định dạng tệp không đúng
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Format: " + e.getMessage());
                alert.showAndWait();
            }
            fileLabel.setPrefHeight(0);
            file = null;
            file2 = null;
            fileLabel.setVisible(false);
        }
    }

    public static class WrongFormatException extends Exception {
        public WrongFormatException(String message) {
            super(message);
        }
    }

    @FXML
    private void createNewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/GUI32.fxml"));
            Node node = fxmlLoader.load();
            GUI32Controller gui32Controller = fxmlLoader.getController();
            gui32Controller.setHeaderListener(this.headerListener);
            gui32Controller.setScreenListener(this.screenListener);
            this.headerListener.hideMenuButton();
            this.headerListener.hideEditingBtn();
            this.headerListener.addAddressToBreadcrumbs("Question bank");
            this.headerListener.addAddressToBreadcrumbs("Questions");
            this.headerListener.addAddressToBreadcrumbs("Editing a Multiple choice question");
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addImg() {
        Image image = new Image(getClass().getResourceAsStream("/img/arrow.png"));
        arrowImg.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImg();
    }


}

