package controller.Ha_Controller;

import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI51Controller implements Initializable {

    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private TextField quizName;
    @FXML
    private ComboBox<Integer> daybox1;

    @FXML
    private ComboBox<Integer> daybox2;

    @FXML
    private ComboBox<Integer> hourbox1;

    @FXML
    private ComboBox<Integer> hourbox2;

    @FXML
    private ComboBox<Integer> minbox1;

    @FXML
    private ComboBox<Integer> minbox2;

    @FXML
    private ComboBox<Integer> minbox3;

    @FXML
    private ComboBox<String> monthbox1;

    @FXML
    private ComboBox<String> monthbox2;

    @FXML
    private ComboBox<String> textbox1;

    @FXML
    private ComboBox<String> textbox2;

    @FXML
    private ComboBox<Integer> yearbox1;

    @FXML
    private ComboBox<Integer> yearbox2;

    private HeaderListener headerListener;

    public void setHeaderListener(HeaderListener headerListener) {
        this.headerListener = headerListener;
    }

    private NewScreenListener screenListener;

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    private void setupMinBox(ComboBox<Integer> comboBox) {
        // Populate the ComboBox with values from 1 to 60.
        for (int i = 0; i <= 59; i++) {
            comboBox.getItems().add(i);
        }
        // Set the default value of the ComboBox to 1.
        comboBox.getSelectionModel().select(0);
    }

    private void setupHourBox(ComboBox<Integer> comboBox) {
        for (int i = 0; i <= 12; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(0);
    }

    private void setupDayBox(ComboBox<Integer> comboBox) {
        for (int i = 1; i <= 31; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(0);
    }

    private void setupYearBox(ComboBox<Integer> comboBox) {
        for (int i = 2000; i <= 2023; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.getSelectionModel().select(0);
    }

    private void setupMonthBox(ComboBox<String> comboBox) {
        // Populate the ComboBox with text options.
        comboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        comboBox.getSelectionModel().selectFirst();
    }

    private void setupTextBox1(ComboBox<String> comboBox) {
        comboBox.getItems().addAll("minutes", "hours");
        comboBox.getSelectionModel().selectFirst();
    }

    private void setupTextBox2(ComboBox<String> comboBox) {
        comboBox.getItems().add("Open attempts are submitted automatically");
        comboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void closeThisWindow(ActionEvent event) {
        this.headerListener.removeAddress(3);
        this.headerListener.showMenuButton();
        this.headerListener.showEditingBtn();
        this.screenListener.removeTopScreen();
    }

    @FXML
    private void createNewQuiz(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
            Node node = fxmlLoader.load();
            CourseListController courseListController = fxmlLoader.getController();
            courseListController.createNewQuiz(quizName.getText());
            courseListController.setHeaderListener(this.headerListener);
            courseListController.setScreenListener(this.screenListener);
            this.headerListener.showMenuButton();
            this.headerListener.showEditingBtn();
            this.headerListener.removeAddress(5);
            this.screenListener.removeTopScreen();
            this.screenListener.changeScreen(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        minbox3.setDisable(true);
        textbox1.setDisable(true);
//        textbox2.setDisable(true);

        // Set up the CheckBox to enable/disable the ComboBoxes.
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                minbox3.setDisable(false);
                textbox1.setDisable(false);
            } else {
                textbox1.setDisable(true);
                minbox3.setDisable(true);
            }
        });
        setupMinBox(minbox1);
        setupMinBox(minbox2);
        setupMinBox(minbox3);
        setupHourBox(hourbox1);
        setupHourBox(hourbox2);
        setupDayBox(daybox1);
        setupDayBox(daybox2);
        setupYearBox(yearbox1);
        setupYearBox(yearbox2);
        setupMonthBox(monthbox1);
        setupMonthBox(monthbox2);
        setupTextBox1(textbox1);
        setupTextBox2(textbox2);
    }
}