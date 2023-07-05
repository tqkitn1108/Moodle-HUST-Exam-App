package controller.Thien_Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import java.util.*;

public class GUI32Controller implements Initializable {

    @FXML
    private Label pageLabel;
    @FXML
    private VBox myVBox;
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
    private Pane pane;
    @FXML
    private Pane pane2;
    @FXML
    private FontAwesomeIconView closeIcon;
    @FXML
    private Label videoPathLabel;

    private Map<String, Integer> categoryLevel;

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
    private String firstCategoryName;

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
        categoryLevel = new HashMap<>();
        for (Category category : dbInteract.getAllNonSubCategories()) {
            showCategoryInTree(category, 0, cateBox);
        }
        if (cateName != null) {
            firstCategoryName = cateName;
            cateBox.setValue(cateName);
            cateBox.setPadding(new Insets(0, 0, 0, -categoryLevel.get(GeneralFunctions.getCateName(cateName)) * 9));
        }
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
    public void saveAndContinueEditing(ActionEvent event) {
        try {
            int count = 0; // Đếm số lượng đáp án được điền, nếu nhỏ hơn 2 thì báo lỗi
            Set<Node> nodes = myVBox.lookupAll(".choice-text");
            List<Image> images = new ArrayList<>();
            List<Character> labels = new ArrayList<>();
            List<String> options = new ArrayList<>();
            List<Double> grades = new ArrayList<>();
            for (Node node : nodes) {
                TextArea textArea = (TextArea) node;
                ImageView imageView = (ImageView) node.getParent().lookup(".image-view");
                if (textArea.getText().length() != 0 || imageView.getImage() != null) {
                    count++;
                    options.add(textArea.getText());
                    labels.add((char) (count + 64));
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
            if (cateBox.getValue() == null) {
                flag = false;
                throw new Exception("Please choose a category!");
            }
            if (quesName.getText().length() == 0) {
                flag = false;
                throw new Exception("Please fill the question name field");
            }
            if (quesText.getText().length() == 0) {
                flag = false;
                throw new Exception("Please fill the question text field");
            }
            if (count < 2) {
                flag = false;
                throw new Exception("The number of choices must be greater than or equal to 2");
            }
            boolean check = false;
            for (Double grade : grades) {
                if (grade > 0) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                flag = false;
                throw new Exception("You must choose at least a grade is not None");
            }
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
            newQuestion.setMediaPath(videoPathLabel.getText());
            newQuestion.setOptionLabels(labels);
            newQuestion.setChoices(choices);
            if (question == null || !quesName.getText().equals(question.getQuestionName())) {
                dbInteract.insertQuestion(newQuestion, GeneralFunctions.getCateName(cateBox.getValue()), null);
                if (question == null) {
                    DataModel.getInstance().getGui21Controller().loadCategoryBox();
                    DataModel.getInstance().getGui21Controller().setValueInCategoryBox1(GeneralFunctions.getCateName(cateBox.getValue()));
                    question = newQuestion;
                } else {
                    dbInteract.deleteQuestion(question.getQuestionName(), null);
                    questionInGUI31Controller.setQuestion(newQuestion);
                    question = newQuestion;
                }
                flag = true;
            } else {
                if (cateBox.getValue().equals(firstCategoryName)) {
                    dbInteract.editQuestion(quesName.getText(), newQuestion);
                } else {
                    dbInteract.deleteQuestion(quesName.getText(), null);
                    dbInteract.insertQuestion(newQuestion, GeneralFunctions.getCateName(cateBox.getValue()), null);
                    DataModel.getInstance().getGui21Controller().loadCategoryBox();
                    DataModel.getInstance().getGui21Controller().setValueInCategoryBox1(GeneralFunctions.getCateName(cateBox.getValue()));
                }
                if (questionInGUI31Controller != null)
                    questionInGUI31Controller.setQuestion(newQuestion);
                flag = true;
            }
        } catch (Exception e) {
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void saveChange(ActionEvent event) {
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
        try {
            Stage thisStage = (Stage) quesImg.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose A Image");
            File file = fileChooser.showOpenDialog(thisStage);
            if (file != null) {
                String fileName = file.getName();
                MFXButton button = (MFXButton) event.getSource();
                if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".gif") && !fileName.endsWith(".mp4")) {
                    throw new Exception("Wrong Format! Please choose a image/video file");
                }
                if (button.getText().equals("Insert Image/Video") && fileName.endsWith(".mp4")) {
                    videoPathLabel.setText(file.getPath());
                    ImageView imageView = new ImageView(new Image("/img/video-play-icon.png"));
                    imageView.setFitHeight(20);
                    imageView.setFitWidth(20);
                    videoPathLabel.setGraphic(imageView);
                    videoPathLabel.setVisible(true);
                } else {
                    if (fileName.endsWith(".mp4")) {
                        throw new Exception("Wrong Format! Please choose a image file");
                    }
                    Image image = new Image(file.toURI().toString());
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
        } catch (Exception e) {
            GeneralFunctions.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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
        if (question.getMediaPath() != null && question.getMediaPath().length() > 0) {
            videoPathLabel.setText(question.getMediaPath());
            ImageView imageView = new ImageView(new Image("/img/video-play-icon.png"));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            videoPathLabel.setGraphic(imageView);
            videoPathLabel.setVisible(true);
        }
        Set<Node> nodes = myVBox.lookupAll(".choice-text");
        List<Choice> choices = question.getChoices();
        if (choices.size() > 2) {
            pane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            pane.setVisible(true);
            if (choices.size() > 5) {
                pane2.setPrefHeight(Region.USE_COMPUTED_SIZE);
                pane2.setVisible(true);
            }
            addChoiceButton.setVisible(false);
        }
        int index = 0;
        for (Node node : nodes) {
            TextArea textArea = (TextArea) node;
            if (choices.get(index).getOption().length() != 0 || choices.get(index).getOptionImage() != null) {
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

    @FXML
    public void setCateNameDisplay(ActionEvent event) {
        cateBox.setPadding(new Insets(0, 0, 0, -categoryLevel.get(GeneralFunctions.getCateName(cateBox.getValue())) * 9));
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
        setUpGradeBox();
        videoPathLabel.setPrefHeight(0);
        videoPathLabel.setVisible(false);
        pane.setPrefHeight(0);
        pane.setVisible(false);
        pane2.setPrefHeight(0);
        pane2.setVisible(false);
        addChoiceButton.setOnAction(actionEvent -> {
            if (pane.getPrefHeight() == 0) {
                pane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                pane.setVisible(true);
            } else {
                pane2.setPrefHeight(Region.USE_COMPUTED_SIZE);
                pane2.setVisible(true);
                addChoiceButton.setVisible(false);
                addChoiceButton.setMinHeight(0);
                addChoiceButton.setPrefHeight(0);
            }
        });
    }
}