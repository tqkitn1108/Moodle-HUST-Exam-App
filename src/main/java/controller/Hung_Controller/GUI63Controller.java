package controller.Hung_Controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Category;
import model.DBInteract;
import model.Question;
import model.Quiz;
import model2.DataModel;
import model2.GeneralFunctions;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GUI63Controller implements Initializable {
    @FXML
    private VBox questionList;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private HBox columnTittle;
    @FXML
    private JFXCheckBox checkBox;
    @FXML
    private JFXCheckBox showMoreQuestionCheckBox;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private DBInteract dbInteract;
    private List<Question> selectedQuestions;
    private List<Question> questionsInCategory;
    private Map<String, Integer> categoryLevel;

    @FXML
    public void closeThisWindow(MouseEvent event) {
        this.screenListener.removeTopScreen();
    }

    @FXML
    public void showQuesFromSubCate(ActionEvent event) throws IOException {
        if (categoryBox.getValue() != null) {
            selectCateBoxItem(event);
        }
    }

    @FXML
    public void selectCateBoxItem(ActionEvent event) throws IOException{
        questionList.getChildren().clear();
        columnTittle.setVisible(true);
        checkBox.setSelected(false);
        setCateNameDisplay();
        questionsInCategory = dbInteract.getQuestionsBelongToCategory(GeneralFunctions.getCateName(categoryBox.getValue()));
        int i = 1;
        for (Question question : questionsInCategory) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI63.fxml"));
            Node node = fxmlLoader.load();
            QuestionInGUI63Controller questionInGUI63Controller = fxmlLoader.getController();
            questionInGUI63Controller.setQuestion(question);
            questionInGUI63Controller.setMainScreen(this.headerListener, this.screenListener);
            if (i % 2 == 1) node.setStyle("-fx-background-color: #fff");
            i++;
            questionList.getChildren().add(node);
        }
        if (showMoreQuestionCheckBox.isSelected()) {
            List<Category> subCategories = dbInteract.getSubCategoriesOf(GeneralFunctions.getCateName(categoryBox.getValue()));
            for (Category category : subCategories) {
                for (Question question : category.getQuestions()) {
                    questionsInCategory.add(question);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI63.fxml"));
                    Node node = fxmlLoader.load();
                    QuestionInGUI63Controller questionInGUI63Controller = fxmlLoader.getController();
                    questionInGUI63Controller.setQuestion(question);
                    questionInGUI63Controller.setMainScreen(this.headerListener, this.screenListener);
                    if (i % 2 == 1) node.setStyle("-fx-background-color: #fff");
                    i++;
                    questionList.getChildren().add(node);
                }
            }
        }
    }
    @FXML
    public void addSelectedQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62.fxml"));
            Node node = fxmlLoader.load();
            GUI62Controller gui62Controller = fxmlLoader.getController();
            Set<Node> allSelectionBoxes = questionList.lookupAll(".selection-box");
            int index =0;
            for (Node node1 : allSelectionBoxes) {
                JFXCheckBox box = (JFXCheckBox) node1;
                if(box.isSelected()) {
                    selectedQuestions.add(questionsInCategory.get(index));
                }
                index++;
            }
            gui62Controller.setSelectedQuestions(selectedQuestions);
            gui62Controller.addQuestionToScrollPane();
            gui62Controller.setMainScreen(this.headerListener, this.screenListener);
            this.screenListener.removeTopScreen();  // Xóa giao diện GUI63
            this.screenListener.removeTopScreen(); // Xóa giao diện GUI62a đã có
            this.screenListener.changeScreen(node); // Load giao diện GUI62a hiện tại
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void selectionAll(ActionEvent event) {
        Set<Node> allSelectionBoxes = questionList.lookupAll(".selection-box");
        if(checkBox.isSelected()) {
            for (Node node : allSelectionBoxes) {
                JFXCheckBox box = (JFXCheckBox) node;
                box.setSelected(true);
            }
        }
        else {
            for (Node node : allSelectionBoxes) {
                JFXCheckBox box = (JFXCheckBox) node;
                box.setSelected(false);
            }
        }
    }

    public void setCateNameDisplay() {
        if (categoryBox.getValue() != null) {
            categoryBox.setPadding(new Insets(0, 0, 0, -categoryLevel.get(GeneralFunctions.getCateName(categoryBox.getValue())) * 9));
        }
    }

    public void showCategoryInTree(Category category, int order) {
        categoryLevel.put(category.getCategoryTitle(), order);
        int quantity = dbInteract.getQuestionsBelongToCategory(category.getCategoryTitle()).size();
        if (quantity == 0)
            categoryBox.getItems().add("   ".repeat(order) + category.getCategoryTitle());
        else
            categoryBox.getItems().add("   ".repeat(order) + category.getCategoryTitle() + " (" + quantity + ")");
        List<Category> subCategories = dbInteract.getSubCategoriesOf(category.getCategoryTitle());
        if (subCategories != null) {
            for (Category subCategory : subCategories) {
                showCategoryInTree(subCategory, order + 1);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        selectedQuestions = new ArrayList<>();
        categoryLevel = new HashMap<>();
        for (Category category : dbInteract.getAllNonSubCategories()) {
            showCategoryInTree(category, 0);
        }
    }
}
