package controller.Thien_Controller;

import controller.Ha_Controller.GUI21Controller;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import model.Category;
import model.DBInteract;
import model.Question;
import model2.Choice;
import model2.DataModel;
import model2.GeneralFunctions;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class GUI32Controller implements Initializable {

    @FXML
    private Label pageLabel;
    @FXML
    private VBox myVBox;
    @FXML
    private VBox addChoiceVBox;
    @FXML
    private TextField quesName;
    @FXML
    private TextArea quesText;
    @FXML
    private ImageView quesImg;
    @FXML
    private ComboBox<String> cateBox;
    @FXML
    private Button addChoiceButton;
    @FXML
    Pane pane = new Pane();
    @FXML
    private FontAwesomeIconView closeIcon;

    private HeaderListener headerListener;
    private NewScreenListener screenListener;

    public void setMainScreen(HeaderListener headerListener, NewScreenListener screenListener) {
        this.headerListener = headerListener;
        this.screenListener = screenListener;
    }

    private String[] gradeArray = {"None", "100%", "90%", "83.33333%", "80%", "75%", "70%", "66.66667%", "60%", "50%", "40%", "33.33333%",
            "30%", "25%", "20%", "16.66667%", "14.28571%", "12.5%", "11.11111%", "10%", "5%", "-5%", "-10%", "-11.11111%", "-12.5%",
            "-14.28571%", "-16.66667%", "-20%", "-25%", "-30%", "-33.33333%", "-40%", "-50%", "-60%", "-66.66667%", "-70%", "-75%", "-80%", "-83.33333%"};

    private DBInteract dbInteract;
    private Question question;
    private QuestionInGUI31Controller questionInGUI31Controller;
    private boolean flag = false;

    public void setQuestionInGUI31Controllers(QuestionInGUI31Controller questionInGUI31Controller) {
        this.questionInGUI31Controller = questionInGUI31Controller;
    }

    public void setPageLabel(String pageLabel) {
        this.pageLabel.setText(pageLabel);
    }

    public void setQuestion(Question question) {
        this.question = question;
        setEditingState();
    }

    public void setCateBox(String cateName) {
        List<Category> categories = dbInteract.getAllCategories();
        for (Category category : categories) {
            int quantity = dbInteract.getQuestionsBelongToCategory(category.getCategoryTitle()).size();
            if (quantity == 0) {
                cateBox.getItems().add(category.getCategoryTitle());
            } else {
                cateBox.getItems().add(category.getCategoryTitle() + " (" + quantity + ")");
            }
        }
        cateBox.setValue(cateName);
    }

    @FXML
    public void closeThisWindow(ActionEvent event) {
        try {
            this.headerListener.showMenuButton();
            this.headerListener.showEditingBtn();
            this.headerListener.removeAddress(3);
            this.screenListener.removeTopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveAndContinueEditing(ActionEvent event) throws Exception {
        int count = 0; // Đếm số lượng đáp án được điền, nếu nhỏ hơn 2 thì báo lỗi
        Set<Node> nodes = myVBox.lookupAll(".choice-text");
        List<Image> images = new ArrayList<>();
        List<Character> labels = new ArrayList<>();
        List<String> options = new ArrayList<>();
        List<Double> grades = new ArrayList<>();
        for (Node node : nodes) {
            TextArea textArea = (TextArea) node;
            if (textArea.getText().length() != 0) {
                count++;
                options.add(textArea.getText());
                labels.add((char) (count + 64));
                ImageView imageView = (ImageView) node.getParent().lookup(".image-view");
                images.add(imageView.getImage());
                Node node1 = node.getParent();
                while (node1 != null) {
                    if (node1.getParent().lookup(".grade-box") == null) {
                        node1 = node1.getParent();
                    } else {
                        ComboBox<String> gradeBox = (ComboBox<String>) node1.getParent().lookup(".grade-box");
                        String tmp;
                        if (!gradeBox.getValue().equals("None")) {
                            tmp = gradeBox.getValue().replace("%", "");
                        } else tmp = "0";
                        grades.add(Double.parseDouble(tmp));
                        break;
                    }
                }
            }
        }
        if (count < 2) {
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "The number of choices must be greater than or equal to 2");
            flag = false;
        } else {
            List<Choice> choices = new ArrayList<>();
            for (int i = 0; i < count; ++i) {
                Choice choice = new Choice();
                choice.setOption(options.get(i));
                choice.setOptionImage(images.get(i));
                choice.setOptionGrade(grades.get(i));
                choices.add(choice);
            }
            Question newQuestion = new Question();
            newQuestion.setQuestionName(quesName.getText());
            newQuestion.setQuestionText(quesText.getText());
            newQuestion.setQuestionImage(quesImg.getImage());
            newQuestion.setOptionLabels(labels);
            newQuestion.setChoices(choices);
            if (question == null || !quesName.getText().equals(question.getQuestionName())) {
                dbInteract.insertQuestion(newQuestion, GeneralFunctions.getCateName(cateBox.getValue()));
                if (question == null) {

                } else {
                    dbInteract.deleteQuestion(question.getQuestionName());
                    questionInGUI31Controller.setQuestion(newQuestion);
                }
            } else {
                dbInteract.editQuestion(quesName.getText(), newQuestion);
                questionInGUI31Controller.setQuestion(newQuestion);
            }
            flag = true;
        }
    }

    @FXML
    public void saveChange(ActionEvent event) throws Exception {
        saveAndContinueEditing(event);
        if (flag)
            closeThisWindow(event);
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
                GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", "Wrong Format! Please choose a image file");
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

    public void setEditingState() {
        this.quesName.setText(question.getQuestionName());
        this.quesText.setText(question.getQuestionText());
        if (question.getQuestionImage() != null) {
            this.quesImg.setImage(question.getQuestionImage());
            this.quesImg.setFitWidth(300);
            this.quesImg.setFitHeight(300);
            this.closeIcon.setVisible(true);
        }
        Set<Node> nodes = myVBox.lookupAll(".choice-text");
        List<Choice> choices = question.getChoices();
        if (choices.size() > 2) {
            pane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            pane.setVisible(true);
            addChoiceButton.setVisible(false);
        }
        int index = 0;
        for (Node node : nodes) {
            TextArea textArea = (TextArea) node;
            if (choices.get(index).getOption().length() != 0) {
                textArea.setText(choices.get(index).getOption());
                ImageView imageView = (ImageView) node.getParent().lookup(".image-view");
                if (choices.get(index).getOptionImage() != null) {
                    imageView.setImage(choices.get(index).getOptionImage());
                    imageView.setFitHeight(300);
                    imageView.setFitWidth(300);
                    Node closeIcon = imageView.getParent().lookup(".close-img-icon");
                    closeIcon.setVisible(true);
                }
                Node node1 = node.getParent();
                while (node1 != null) {
                    if (node1.getParent().lookup(".grade-box") == null) {
                        node1 = node1.getParent();
                    } else {
                        ComboBox<String> gradeBox = (ComboBox<String>) node1.getParent().lookup(".grade-box");
                        if (choices.get(index).getOptionGrade() != 0D) {
                            double grade = choices.get(index).getOptionGrade();
                            String tmp;
                            if (grade == (int) grade) {
                                tmp = String.format("%d", (int) grade);
                            } else tmp = String.format("%.5f", grade).replace(",", ".");
                            tmp += "%";
                            gradeBox.setValue(tmp);
                        }
                        break;
                    }
                }
            }
            index++;
            if (index == choices.size()) break;
        }
    }

    public void setUpGradeBox() {
        Set<Node> gradeBoxes = myVBox.lookupAll(".grade-box");
        for (Node node : gradeBoxes) {
            ComboBox<String> gradeBox = (ComboBox<String>) node;
            gradeBox.getItems().addAll(gradeArray);
            gradeBox.getSelectionModel().selectFirst();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbInteract = DataModel.getInstance().getDbInteract();
        setUpGradeBox();
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