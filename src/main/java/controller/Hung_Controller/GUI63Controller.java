package controller.Hung_Controller;

import com.jfoenix.controls.JFXCheckBox;
import controller.Thien_Controller.GUI32Controller;
import controller.Thien_Controller.QuestionInGUI31Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Category;
import model.DBInteract;
import model.Question;
import model2.DataModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class GUI63Controller implements Initializable {
    @FXML
    private VBox questionList;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private HBox columnTittle;
    @FXML
    private JFXCheckBox checkBox;
    private HeaderListener headerListener;
    private NewScreenListener screenListener;
    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener){
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private DBInteract dbInteract;
    private String quizName;
    private List<Question> selectedQuestions;
    private List<Question> questionsInCategory;

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    @FXML
    public void closeThisWindow(MouseEvent event) {
        this.screenListener.removeTopScreen();
    }

    @FXML
    public void selectCateBoxItem(ActionEvent event) throws IOException{
        questionList.getChildren().clear();
        columnTittle.setVisible(true);
        questionsInCategory = dbInteract.getQuestionsBelongToCategory(getCateName(categoryBox.getValue()));
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
    }
    @FXML
    public void addSelectedQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62a.fxml"));
            Node node = fxmlLoader.load();
            GUI62aController gui62aController = fxmlLoader.getController();
            gui62aController.setQuizName(quizName);
            Set<Node> allSelectionBoxes = questionList.lookupAll(".selection-box");
            int index =0;
            for (Node node1 : allSelectionBoxes) {
                JFXCheckBox box = (JFXCheckBox) node1;
                if(box.isSelected()) {
                    selectedQuestions.add(questionsInCategory.get(index));
                }
                index++;
            }
            gui62aController.setSelectedQuestions(selectedQuestions);
            gui62aController.setTotalOfMark();
            gui62aController.addQuestionToScrollPane();
            gui62aController.setMainScreen(this.headerListener, this.screenListener);
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

    public String getCateName(String nameWithQuantity) {
        if (nameWithQuantity.charAt(nameWithQuantity.length() - 1) == ')') {
            return nameWithQuantity.substring(0, nameWithQuantity.lastIndexOf('(') - 1);
        }
        return nameWithQuantity;
    }

    public void addCategoryBox() {
        List<Category> categories = dbInteract.getAllCategories();
        for (Category category : categories) {
            int quantity = dbInteract.getQuestionsBelongToCategory(category.getCategoryTitle()).size();
            if (quantity == 0) {
                categoryBox.getItems().add(category.getCategoryTitle());
            } else {
                categoryBox.getItems().add(category.getCategoryTitle() + " (" + quantity + ")");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        selectedQuestions = new ArrayList<>();
        addCategoryBox();
    }
}
