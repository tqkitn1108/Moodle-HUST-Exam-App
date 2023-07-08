package controller.Ha_Controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;
import model.DBInteract;
import model.DataModel;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable {
    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;
    HeaderController headerController2;

    private void addScreenToStackPane(Node node) {
        if (this.stackPane.getChildren().contains(node)) {
            this.stackPane.getChildren().remove(node);
        }
        this.stackPane.getChildren().add(node);
    }

    private void setHeader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/Header.fxml"));
            Node node = fxmlLoader.load();
            HeaderController headerController = fxmlLoader.getController();
            headerController2 = headerController;
            headerController.setMainScreen(new HeaderListener() {
                @Override
                public void handle(Event event) {

                }

                @Override
                public void addAddressToBreadcrumbs(String address) {
                    MFXButton button = new MFXButton();
                    button.setText(address);
                    Text text = new Text("/");
                    button.setStyle("-fx-font-size: 15px; -fx-text-fill: #c53624;");
                    button.setCursor(Cursor.HAND);
                    text.setStyle("-fx-font-size: 15px");
                    button.setGraphic(text);
                    button.setGraphicTextGap(10);
                    button.setContentDisplay(ContentDisplay.LEFT);
                    button.setPadding(new Insets(5));
                    button.setRippleColor(Paint.valueOf("#fff"));
                    headerController.getBreadcrumbs().getChildren().add(button);
                }

                @Override
                public void removeAddress(Integer number) {
                    HBox breadcrumbs = headerController.getBreadcrumbs();
                    if (breadcrumbs.getChildren().size() - number >= 3) {
                        while (number > 0) {
                            breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
                            number--;
                        }
                    } else {
                        while (breadcrumbs.getChildren().size() > 3) {
                            breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
                        }
                    }
                }

                @Override
                public void showEditingBtn() {
                    headerController.getEditingBtn().setVisible(true);
                }

                @Override
                public void hideEditingBtn() {
                    headerController.getEditingBtn().setVisible(false);
                }

                @Override
                public void showMenuButton() {
                    headerController.getMenuBtn().setVisible(true);
                }

                @Override
                public void hideMenuButton() {
                    headerController.getMenuBtn().setVisible(false);
                }
            }, new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                }

                @Override
                public void handle(Event event) {

                }
            });
            header.getChildren().add(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addQuizList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/CourseList.fxml"));
            Node node = fxmlLoader.load();
            CourseListController courseListController = fxmlLoader.getController();
            courseListController.setMainScreen(new HeaderListener() {
                @Override
                public void handle(Event event) {

                }

                @Override
                public void addAddressToBreadcrumbs(String address) {
                    MFXButton button = new MFXButton();
                    button.setText(address);
                    Text text = new Text("/");
                    button.setStyle("-fx-font-size: 15px; -fx-text-fill: #c53624;");
                    button.setCursor(Cursor.HAND);
                    text.setStyle("-fx-font-size: 15px");
                    button.setGraphic(text);
                    button.setGraphicTextGap(10);
                    button.setContentDisplay(ContentDisplay.LEFT);
                    button.setPadding(new Insets(5));
                    button.setRippleColor(Paint.valueOf("#fff"));
                    headerController2.getBreadcrumbs().getChildren().add(button);
                }

                @Override
                public void removeAddress(Integer number) {
                    HBox breadcrumbs = headerController2.getBreadcrumbs();
                    if (breadcrumbs.getChildren().size() - number >= 3) {
                        while (number > 0) {
                            breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
                            number--;
                        }
                    } else {
                        while (breadcrumbs.getChildren().size() > 3) {
                            breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
                        }
                    }
                }

                @Override
                public void showEditingBtn() {
                    headerController2.getEditingBtn().setVisible(true);
                }

                @Override
                public void hideEditingBtn() {
                    headerController2.getEditingBtn().setVisible(false);
                }

                @Override
                public void showMenuButton() {
                    headerController2.getMenuBtn().setVisible(true);
                }

                @Override
                public void hideMenuButton() {
                    headerController2.getMenuBtn().setVisible(false);
                }
            }, new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addScreenToStackPane(node);
                }

                @Override
                public void removeTopScreen() {
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                }

                @Override
                public void handle(Event event) {

                }
            });
            stackPane.getChildren().add(node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBInteract dbInteract = new DBInteract();
        DataModel.getInstance().setDbInteract(dbInteract);
        setHeader();
        addQuizList();
    }
}
