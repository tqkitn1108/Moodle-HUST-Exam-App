package controller.Ha_Controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class GUI51Controller {

    @FXML
    private CheckBox checkBox;
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

    //Set up minbox
    public void initialize() {
        // Disable the ComboBoxes by default.
        minbox3.setDisable(true);
        textbox1.setDisable(true);
        textbox2.setDisable(true);

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

    private void setupMinBox(ComboBox<Integer> comboBox) {
        // Populate the ComboBox with values from 1 to 60.
        for (int i = 0; i <= 59; i++) {
            comboBox.getItems().add(i);
            // Set the default value of the ComboBox to 1.
        }
        // Set the default value of the ComboBox to 1.
        comboBox.getSelectionModel().select(0);
    }

    //Set up hourbox
    private void setupHourBox(ComboBox<Integer> comboBox) {
        // Populate the ComboBox with values from 1 to 60.
        for (int i = 0; i <= 12; i++) {
            comboBox.getItems().add(i);
        }
        // Set the default value of the ComboBox to 1.
        comboBox.getSelectionModel().select(0);

    }

    // Set up daybox
    private void setupDayBox(ComboBox<Integer> comboBox) {
        // Populate the ComboBox with values from 1 to 60.
        for (int i = 1; i <= 31; i++) {
            comboBox.getItems().add(i);
        }
        // Set the default value of the ComboBox to 1.
        comboBox.getSelectionModel().select(0);
    }

    //Set up yearbox
    private void setupYearBox(ComboBox<Integer> comboBox) {
        // Populate the ComboBox with values from 1 to 60.
        for (int i = 2000; i <= 2023; i++) {
            comboBox.getItems().add(i);
        }
        // Set the default value of the ComboBox to 1.
        comboBox.getSelectionModel().select(0);
    }

    //Set up monthbox

    private void setupMonthBox(ComboBox<String> comboBox) {
         // Populate the ComboBox with text options.
        comboBox.getItems().addAll("January", "February", "March","April","May","June","July","August","September","October","November","December");

        // Set the default value of the ComboBox to the first option.
        comboBox.getSelectionModel().selectFirst();

    }

    // Set up textbox1

    private void setupTextBox1(ComboBox<String> comboBox) {
         // Populate the ComboBox with text options.
        comboBox.getItems().addAll("minutes", "hours");

        // Set the default value of the ComboBox to the first option.
        comboBox.getSelectionModel().selectFirst();

    }

    // Set up textbox2
    private void setupTextBox2(ComboBox<String> comboBox) {
         // Populate the ComboBox with text options.
        comboBox.getItems().addAll("Open attempts are submitted automatically");

        // Set the default value of the ComboBox to the first option.
        comboBox.getSelectionModel().selectFirst();

    }
}
