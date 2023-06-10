package controller.Thien_Controller;

import controller.Ha_Controller.GUI21Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI32Controller implements Initializable {

    @FXML
    private VBox myVBox;
    @FXML
    private VBox addChoiceVBox;
    @FXML
    private ComboBox<String> gradeComboBox1;
    @FXML
    private ComboBox<String> gradeComboBox2;
    @FXML
    private ComboBox<String> gradeComboBox3;
    @FXML
    private ComboBox<String> gradeComboBox4;
    @FXML
    private ComboBox<String> gradeComboBox5;
    @FXML
    private Button addChoiceButton;
    @FXML
    Pane pane = new Pane();
    @FXML
    private TextArea quesText;
    @FXML
    private ImageView quesImg;
    @FXML
    private FontAwesomeIconView closeIcon;

    private HeaderListener headerListener;

    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    private String[] grade = {"None", "100%", "90%", "83,33333%", "80%", "75%", "70%", "66.66667%", "60%", "50%", "40%", "33.33333%",
            "30%", "25%", "20%", "16.66667%", "14.28571%", "12.5%", "11.11111%", "10%", "5%", "-5%", "-10%", "-11.11111%", "-12.5%",
            "-14.28571%", "-16.66667%", "-20%", "-25%", "-30%", "-33.33333%", "-40%", "-50%", "-60%", "-66.66667%", "-70%", "-75%", "-80%", "-83,33333%"};

    @FXML
    public void closeThisWindow(ActionEvent event) {
        try {
            this.headerListener.showMenuButton();
            this.screenListener.removeTopScreen();
            this.headerListener.removeAddress(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pasteImgToQuesText(KeyEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (event.isShortcutDown() && event.getCode() == KeyCode.V) {
            if (clipboard.hasImage()) {
                Image image = clipboard.getImage();
                quesImg.setImage(image);
                quesImg.setFitHeight(300);
                quesImg.setFitWidth(300);
                closeIcon.setVisible(true);
                event.consume();
            }
        }
    }

    @FXML
    public void openFileChooser(ActionEvent event) {
        Stage thisStage = (Stage) quesImg.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose A Image");
        File file = fileChooser.showOpenDialog(thisStage);
        if (file != null) {
            String fileName = file.getName();
            if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Format! Please choose a image file");
                alert.showAndWait();
            } else {
                Image image = new Image(file.toURI().toString());
                Node button = (Node) event.getSource();
                Node parent = button.getParent();
                ImageView imageView = (ImageView) parent.lookup(".image-view");
                Node closeIcon = parent.lookup(".close-img-icon");
                closeIcon.setVisible(true);
                imageView.setImage(image);
                imageView.setFitHeight(300);
                imageView.setFitWidth(300);
                event.consume();
            }
        }
    }

    @FXML
    public void removeImg(MouseEvent event) {
        Node closeIcon = (Node) event.getSource();
        Node parent = closeIcon.getParent();
        ImageView imageView = (ImageView) parent.lookup(".image-view");
        imageView.setImage(null);
        imageView.setFitHeight(75);
        imageView.setFitWidth(250);
        closeIcon.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeComboBox1.getItems().addAll(grade);
        gradeComboBox2.getItems().addAll(grade);
        gradeComboBox3.getItems().addAll(grade);
        gradeComboBox4.getItems().addAll(grade);
        gradeComboBox5.getItems().addAll(grade);
        pane.setPrefHeight(0);
        pane.setVisible(false);
        addChoiceButton.setOnAction(actionEvent -> {
            pane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            pane.setVisible(true);
            addChoiceButton.setVisible(false);
            addChoiceButton.setMinHeight(0);
            addChoiceButton.setPrefHeight(0);
        });
    }
}