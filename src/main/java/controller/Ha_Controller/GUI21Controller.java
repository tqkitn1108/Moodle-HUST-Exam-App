package controller.Ha_Controller;

import com.jfoenix.controls.JFXCheckBox;
import controller.Thien_Controller.GUI32Controller;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI21Controller implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private AnchorPane quesListPane;

    @FXML
    private ImageView arrowImg;


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
    private void createNewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/GUI32.fxml"));
            Node node = fxmlLoader.load();
            GUI32Controller gui32Controller = fxmlLoader.getController();
            gui32Controller.setHeaderListener(this.headerListener);
            gui32Controller.setScreenListener(this.screenListener);
            this.headerListener.hideMenuButton();
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
