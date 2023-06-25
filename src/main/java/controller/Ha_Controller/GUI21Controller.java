package controller.Ha_Controller;

import com.jfoenix.controls.JFXCheckBox;
import controller.Thien_Controller.GUI32Controller;
import controller.Thien_Controller.QuestionInGUI31Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Category;
import model.DBInteract;
import model.DataInteract;
import model.Question;
import model2.DataModel;
import model2.GeneralFunctions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GUI21Controller implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private VBox questionList;
    @FXML
    private HBox columnTittle;
    @FXML
    private ImageView arrowImg;
    @FXML
    private MFXButton chooseBtn;
    @FXML
    private VBox boxToDropFile;
    @FXML
    private TextField cateID;
    @FXML
    private TextArea cateInfo;
    @FXML
    private TextField cateName;
    @FXML
    private Label desLabel;
    @FXML
    private Label fileLabel;
    @FXML
    private ComboBox<String> categoryBox1;
    @FXML
    private ComboBox<String> categoryBox2;
    @FXML
    private ComboBox<String> categoryBox3;

    private DBInteract dbInteract;
    private File file, file2;

    private Map<String, Integer> categoryLevel;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    public TabPane getTabPane() {
        return tabPane;
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
    public void chooseAFile(ActionEvent event) {
        Stage thisStage = (Stage) chooseBtn.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        file2 = fileChooser.showOpenDialog(thisStage);
        if (file2 != null) {
            this.file = file2;
            addFileLabel(file.getName());
        }
    }

    @FXML
    public void dropFile(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            dragboard.getFiles().forEach(file -> {
                this.file = file;
                addFileLabel(file.getName());
            });
        }
        event.setDropCompleted(true);
        event.consume();
    }

    @FXML
    public void importFile(ActionEvent event) {
        if (categoryBox3.getSelectionModel().getSelectedItem() == null) {
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "No category is chosen, try again!");
        } else if (file == null && file2 == null) {
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "No file is chosen, try again!");
        } else if (file != null) {
            try {
                String fileName = file.getName();
                // Kiểm tra định dạng tệp
                if (!fileName.endsWith(".txt") && !fileName.endsWith(".docx") && !fileName.endsWith(".doc")) {
                    throw new WrongFormatException("Please choose a file with tail .txt, .doc or .docx");
                }
                // Đọc nội dung tệp và xử lý
                String itemWithOldQuantity = categoryBox3.getValue();
                String cateTitle = GeneralFunctions.getCateName(itemWithOldQuantity);
                List<Question> quesList = new ArrayList<>();
                if (fileName.endsWith(".txt")) {
                    quesList = DataInteract.getQuestionsFromTxtFile(file.getPath());
                }
                if (fileName.endsWith(".docx") || fileName.endsWith(".doc")) {
                    quesList = DataInteract.getQuestionsFromDocFile(file.getPath());
                }
                for (Question q : quesList) {
                    dbInteract.insertQuestion(q, cateTitle);
                }
                loadCategoryBox();
                categoryBox3.setValue(cateTitle + " (" + dbInteract.getQuestionsBelongToCategory(cateTitle).size() + ")");

                // Xử lý khi import thành công
                GeneralFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Import successfully!");
            } catch (WrongFormatException e) {
                // Xử lý khi định dạng tệp không đúng
                GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Wrong Format: " + e.getMessage());
            } catch (Exception e) {
                GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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
    public void showQuesFromSubCate() throws Exception {
        if (categoryBox1.getValue() != null) {
            selectItemInCateBox();
        }
    }

    @FXML
    public void selectItemInCateBox() throws Exception {
        questionList.getChildren().clear();
        columnTittle.setVisible(true);
        setCateNameDisplay(categoryBox1);
        int i = 1;
        List<Question> questions = dbInteract.getQuestionsBelongToCategory(GeneralFunctions.getCateName(categoryBox1.getValue()));
        for (Question question : questions) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/QuestionInGUI31.fxml"));
            Node node = fxmlLoader.load();
            QuestionInGUI31Controller questionInGUI31Controller = fxmlLoader.getController();
            questionInGUI31Controller.setQuestion(question);
            questionInGUI31Controller.setCateNameWithNum(categoryBox1.getValue());
            questionInGUI31Controller.setQuestionInGUI31Controller(questionInGUI31Controller);
            questionInGUI31Controller.setMainScreen(this.headerListener, this.screenListener);
            if (i % 2 == 1) node.setStyle("-fx-background-color: #fff");
            i++;
            questionList.getChildren().add(node);
        }
        if (checkBox.isSelected()) {
            List<Category> subCategories = dbInteract.getSubCategoriesOf(GeneralFunctions.getCateName(categoryBox1.getValue()));
            for (Category category : subCategories) {
                for (Question question : category.getQuestions()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/QuestionInGUI31.fxml"));
                    Node node = fxmlLoader.load();
                    QuestionInGUI31Controller questionInGUI31Controller = fxmlLoader.getController();
                    questionInGUI31Controller.setQuestion(question);
                    questionInGUI31Controller.setCateNameWithNum(category.getCategoryTitle() + " (" + category.getQuestions().size() + ")");
                    questionInGUI31Controller.setQuestionInGUI31Controller(questionInGUI31Controller);
                    questionInGUI31Controller.setMainScreen(this.headerListener, this.screenListener);
                    if (i % 2 == 1) node.setStyle("-fx-background-color: #fff");
                    i++;
                    questionList.getChildren().add(node);
                }
            }
        }
    }

    @FXML
    private void createNewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Thien_FXML/GUI32.fxml"));
            Node node = fxmlLoader.load();
            GUI32Controller gui32Controller = fxmlLoader.getController();
            gui32Controller.setCateBox(categoryBox1.getValue());
            gui32Controller.setMainScreen(this.headerListener, this.screenListener);
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

    @FXML
    public void addCategory(ActionEvent event) {
        if (cateName.getText().length() > 0) {
            dbInteract.createNewCategory(GeneralFunctions.getCateName(categoryBox2.getValue()), cateID.getText(), cateName.getText());
            String presentValue = categoryBox1.getValue();
            loadCategoryBox();
            categoryBox1.setValue(presentValue);
            GeneralFunctions.showAlert(Alert.AlertType.INFORMATION, "Success", "Add category successfully!");
        } else
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please type category name!");
    }

    @FXML
    public void selectCategory(ActionEvent event) {
        setCateNameDisplay((ComboBox<String>) event.getSource());
    }

    public void setCateNameDisplay(ComboBox<String> comboBox) {
        comboBox.setPadding(new Insets(0, 0, 0, -categoryLevel.get(GeneralFunctions.getCateName(comboBox.getValue())) * 9));
    }

    public void addImg() {
        Image image = new Image(getClass().getResourceAsStream("/img/arrow.png"));
        arrowImg.setImage(image);
    }

    public void loadCategoryBox() {
        categoryBox1.getItems().clear();
        categoryBox2.getItems().clear();
        categoryBox3.getItems().clear();
        categoryLevel = new HashMap<>();
        for (Category category : dbInteract.getAllNonSubCategories()) {
            showCategoryInTree(category, 0, categoryBox1);
            showCategoryInTree(category, 0, categoryBox2);
            showCategoryInTree(category, 0, categoryBox3);
        }
    }

    public void showCategoryInTree(Category category, int order, ComboBox<String> comboBox) {
        categoryLevel.put(category.getCategoryTitle(), order);
        int quantity = dbInteract.getQuestionsBelongToCategory(category.getCategoryTitle()).size();
        if (quantity == 0)
            comboBox.getItems().add("   ".repeat(order) + category.getCategoryTitle());
        else
            comboBox.getItems().add("   ".repeat(order) + category.getCategoryTitle() + " (" + quantity + ")");
        List<Category> subCategories = dbInteract.getSubCategoriesOf(category.getCategoryTitle());
        if (subCategories != null) {
            for (Category subCategory : subCategories) {
                showCategoryInTree(subCategory, order + 1, comboBox);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        addImg();
        loadCategoryBox();
    }
}