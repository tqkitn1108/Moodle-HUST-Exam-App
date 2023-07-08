package controller.Hung_Controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.Category;
import model.DBInteract;
import model.Question;
import model.DataModel;
import model.GeneralFunctions;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GUI65Controller implements Initializable {
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private ComboBox<Integer> randomQuestionBox;
    @FXML
    private VBox questionList;
    @FXML
    private JFXCheckBox showMoreQuestionCheckBox;

    private DBInteract dbInteract;
    private Map<String, Integer> categoryLevel;
    private List<Question> questionsInCategory;
    private List<HBox> hboxList;

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    @FXML
    public void closeThisWindow() {
        this.screenListener.removeTopScreen();
    }

    @FXML
    public void showQuesFromSubCate(ActionEvent event) throws IOException {
        if (categoryBox.getValue() != null) {
            selectCateBoxItem(event);
        }
    }

    @FXML
    public void selectCateBoxItem(ActionEvent event) throws IOException {
        questionList.getChildren().clear();
        questionList.setVisible(true);
        randomQuestionBox.getItems().clear();
        setCateNameDisplay();
        int i = 0;
        hboxList = new ArrayList<>();
        questionsInCategory = dbInteract.getQuestionsBelongToCategory(GeneralFunctions.getCateName(categoryBox.getValue()));
        for (Question question : questionsInCategory) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI65.fxml"));
            Node node = fxmlLoader.load();
            QuestionInGUI65Controller questionInGUI65Controller = fxmlLoader.getController();
            questionInGUI65Controller.setQuestionContent(question);
            i++;
            randomQuestionBox.getItems().add(i);
            hboxList.add((HBox) node);
        }
        if (showMoreQuestionCheckBox.isSelected()) {
            List<Category> subCategories = dbInteract.getSubCategoriesOf(GeneralFunctions.getCateName(categoryBox.getValue()));
            for (Category category : subCategories) {
                for (Question question : category.getQuestions()) {
                    questionsInCategory.add(question);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/QuestionInGUI65.fxml"));
                    Node node = fxmlLoader.load();
                    QuestionInGUI65Controller questionInGUI65Controller = fxmlLoader.getController();
                    questionInGUI65Controller.setQuestionContent(question);
                    i++;
                    randomQuestionBox.getItems().add(i);
                    hboxList.add((HBox) node);
                }
            }
        }
        randomQuestionBox.getSelectionModel().selectFirst();
        if (hboxList.size() > 15) {
            int itemsPerPage = 15;
            int numberOfPage = hboxList.size() % itemsPerPage == 0 ? hboxList.size() / itemsPerPage : hboxList.size() / itemsPerPage + 1;
            Pagination pagination = new Pagination(numberOfPage, 0);
            pagination.setPrefHeight(540);
            VBox vboxOfPagination = new VBox();
            pagination.setPageFactory(pageIndex -> {
                vboxOfPagination.getChildren().clear();
                int start = pageIndex * itemsPerPage;
                int end = Math.min(start + itemsPerPage, hboxList.size());
                for (int j = start; j < end; j++) {
                    vboxOfPagination.getChildren().add(hboxList.get(j));
                }
                return vboxOfPagination;
            });
            questionList.getChildren().add(pagination);
        } else {
            for (HBox hbox : hboxList) {
                questionList.getChildren().add(hbox);
            }
        }
    }

    @FXML
    public void addRandomQuestion(ActionEvent event) throws IOException {
        if (questionList.getChildren().size() > 0) {
            List<Question> randomSublist = new ArrayList<>(questionsInCategory);
            Collections.shuffle(randomSublist);
            randomSublist = randomSublist.subList(0, randomQuestionBox.getValue());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Hung_FXML/GUI62.fxml"));
            Node node = fxmlLoader.load();
            GUI62Controller gui62Controller = fxmlLoader.getController();
            gui62Controller.setSelectedQuestions(randomSublist);
            gui62Controller.setMainScreen(this.headerListener, this.screenListener);
            this.screenListener.removeTopScreen();  // Xóa giao diện GUI65
            this.screenListener.removeTopScreen(); // Xóa giao diện GUI62a đã có
            this.screenListener.changeScreen(node); // Load giao diện GUI62a hiện tại
        } else GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Please select a category");
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
        categoryLevel = new HashMap<>();
        for (Category category : dbInteract.getAllNonSubCategories()) {
            showCategoryInTree(category, 0);
        }
    }
}
