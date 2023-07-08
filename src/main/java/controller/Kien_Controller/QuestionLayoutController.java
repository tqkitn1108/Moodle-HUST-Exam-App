package controller.Kien_Controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import model.Question;
import model.Quiz;
import model2.Choice;

import java.io.File;
import java.net.URL;
import java.util.*;

public class QuestionLayoutController implements Initializable {
    @FXML
    private Label questionNum;
    @FXML
    private Label stateQues;
    @FXML
    private Label flag;
    public VBox questionBox;
    @FXML
    private Label questionContent;
    @FXML
    private ImageView questionImg;
    @FXML
    private AnchorPane correctAnswerPane;
    @FXML
    private MediaView mediaView;

    private Quiz quiz;
    private List<Integer> correctAnswerList;
    private List<Choice> choices;
    private ToggleGroup choiceGroup;
    private Set<CheckBox> checkBoxGroup;
    public MediaPlayer mediaPlayer;
    boolean isPlaying = false;

    public ToggleGroup getChoiceGroup() {
        return choiceGroup;
    }

    public Set<CheckBox> getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public List<Integer> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setQuestion(Question question) {
        setQuestionContent(question.getQuestionName() + ": " + question.getQuestionText(), question.getQuestionImage(), question.getMediaPath());
        setChoices(question);
    }

    public void setStateQues(String state) {
        this.stateQues.setText(state);
    }

    public void setQuestionNum(Integer number) {
        this.questionNum.setText(number.toString());
    }

    public void setQuestionContent(String questionContent, Image image, String mediaPath) {
        this.questionContent.setText(questionContent);
        this.questionContent.setWrapText(true);
        questionImg.setImage(image);
        if (image != null && image.getHeight() > 350) {
            questionImg.setFitHeight(350);
            questionImg.setPreserveRatio(true);
        }
        if (mediaPath != null) {
            if (mediaPath.endsWith(".mp4")) {
                File file = new File(mediaPath.replace("\\", "/"));
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaView.setFitHeight(350);
                mediaView.setFitWidth(500);
            } else if (mediaPath.endsWith(".gif")) {
                Image image1 = new Image(mediaPath);
                questionImg.setImage(image1);
                if (image1.getHeight() > 350) {
                    questionImg.setFitHeight(350);
                    questionImg.setPreserveRatio(true);
                }
            }
        }
    }

    public void setChoices(Question question) {
        choiceGroup = new ToggleGroup();
        correctAnswerList = new ArrayList<>();
        choices = question.getChoices();
        if (quiz.isShuffle()) {
            Collections.shuffle(choices);
        }
        List<Character> labels = question.getOptionLabels();
        if (!question.isMultipleAnswer()) {
            for (int i = 0; i < choices.size(); ++i) {
                JFXRadioButton choice = new JFXRadioButton();
                choice.setText(labels.get(i) + ". " + choices.get(i).getOption() + "             "); // Khoảng trắng để fix trường hợp từ cuối cùng của text ngắn và độ dài của text vừa khớp với độ dài của node cha, khiến từ cuối bị ẩn đi
                Image image = choices.get(i).getOptionImage();
                ImageView imageView = new ImageView(image);
                if (image != null && image.getHeight() > 300) {
                    imageView.setFitHeight(300);
                    imageView.setPreserveRatio(true);
                }
                choice.setGraphic(imageView);
                choice.setToggleGroup(choiceGroup);
                choice.setWrapText(true);
                choice.setMaxWidth(850);
                choice.setContentDisplay(ContentDisplay.RIGHT);
                if (choices.get(i).getOptionGrade() > 0) correctAnswerList.add(i);
                VBox.setMargin(choice, new Insets(7, 20, 0, 20));
                questionBox.getChildren().add(choice);
            }
        } else {
            checkBoxGroup = new LinkedHashSet<>();
            for (int i = 0; i < choices.size(); ++i) {
                CheckBox choice = new CheckBox();
                choice.setText(labels.get(i) + ". " + choices.get(i).getOption() + "             ");
                Image image = choices.get(i).getOptionImage();
                ImageView imageView = new ImageView(image);
                if (image != null && image.getHeight() > 300) {
                    imageView.setFitHeight(300);
                    imageView.setPreserveRatio(true);
                }
                choice.setGraphic(imageView);
                checkBoxGroup.add(choice);
                choice.setWrapText(true);
                choice.setMaxWidth(850);
                choice.setContentDisplay(ContentDisplay.RIGHT);
                choice.getStyleClass().add("choice");
                choice.setCursor(Cursor.HAND);
                if (choices.get(i).getOptionGrade() > 0) correctAnswerList.add(i);
                VBox.setMargin(choice, new Insets(7, 20, 0, 20));
                questionBox.getChildren().add(choice);
            }
        }
    }

    public void setAddAnswer(Question question) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/CorrectAns.fxml"));
        try {
            Node node = fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 15.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            CorrectAnsController correctAnsController = fxmlLoader.getController();
            correctAnsController.setAnswerList(question.getOptionLabels(), choices, correctAnswerList);
            correctAnswerPane.getChildren().add(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void playVideo(MouseEvent event) {
        if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration())) {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
            isPlaying = true;
        } else {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                mediaPlayer.play();
                isPlaying = true;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
