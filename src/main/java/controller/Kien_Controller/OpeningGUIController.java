package controller.Kien_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class OpeningGUIController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private MFXButton openAppBtn;
    @FXML
    private MFXTextField nameField;

    private Parent root;
    private Text userName;

    @FXML
    void coloring(MouseEvent event) {
        openAppBtn.setStyle("-fx-background-color: #009fe5");
    }

    @FXML
    void uncoloring(MouseEvent event) {
        openAppBtn.setStyle("-fx-background-color: #aaaaaa");
    }

    @FXML
    public void openApp(ActionEvent event){
        Stage thisStage = (Stage) openAppBtn.getScene().getWindow();
        if (nameField.getText().length() > 0) {
            userName.setText(nameField.getText());
        } else userName.setText("Unknown Username");
        Stage stage = new Stage();
        stage.setTitle("PHẦN MỀM TẠO NGÂN HÀNG ĐỀ VÀ TỔ CHỨC THI TRẮC NGHIỆM");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(new Image("/img/opening-image.jpg"));
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HA_FXML/GUI11.fxml")));
            userName = (Text) root.lookup(".user-name");;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
