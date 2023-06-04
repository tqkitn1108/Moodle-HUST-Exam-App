package controller.Thien_Controller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI32Controller implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;
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

    private HeaderListener headerListener;
    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }
    private NewScreenListener screenListener;
    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }
    private String[] grade = {"None","100%","90%","83,33333%","80%","75%","70%","66.66667%","60%","50%","40%","33.33333%",
            "30%","25%","20%","16.66667%","14.28571%","12.5%","11.11111%","10%","5%","-5%","-10%","-11.11111%","-12.5%",
            "-14.28571%","-16.66667%","-20%","-25%","-30%","-33.33333%","-40%","-50%","-60%","-66.66667%","-70%","-75%","-80%","-83,33333%"};
    @FXML
    public void closeThisWindow(ActionEvent event) {
        try {
            this.headerListener.showMenuButton();
            this.screenListener.removeTopScreen();
            this.headerListener.removeAddress(3);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        scrollPane.setPrefHeight(1250);
        myVBox.setPrefHeight(1250);
        addChoiceButton.setOnAction(actionEvent -> {
            pane.setPrefHeight(750);
            pane.setVisible(true);
            scrollPane.setPrefHeight(2000);
            myVBox.setPrefHeight(2000);
            addChoiceButton.setVisible(false);
            addChoiceButton.setMinHeight(0);
            addChoiceButton.setPrefHeight(0);
        });
    }
}