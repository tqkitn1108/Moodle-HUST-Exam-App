package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import listeners.HeaderListener;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable {
    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;
    HeaderController headerController2;

    public void loadGUI21() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
            GUI21Controller gui21Controller = fxmlLoader.getController();
            gui21Controller.setScreenListener(new NewScreenListener() {
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
//            for (int i = 0; i < listBtn.getChildren().size(); ++i) {
//                if (listBtn.getChildren().get(i) instanceof Button) {
//                    Button button = (Button) listBtn.getChildren().get(i);
//                    int finalI = i;
//                    button.setOnAction(event -> {
//                        gui21Controller.getTabPane().getSelectionModel().select(finalI);
//                        gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
//                        stackPane.getChildren().add(node);
//                    });
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addScreenToStackPane(Node node) {
        this.stackPane.getChildren().add(node);
    }

    private void setHeader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/Header.fxml"));
            Node node = fxmlLoader.load();
            HeaderController headerController = fxmlLoader.getController();
            headerController2 = headerController;
            headerController.setHeaderListener(new HeaderListener() {
                @Override
                public void handle(Event event) {

                }

                @Override
                public void addAddressToBreadcrumbs(String address) {
                    Label label = new Label(address);
                    Text text = new Text("/");
                    label.setStyle("-fx-font-size: 15px; -fx-text-fill: #c53624;");
                    label.setCursor(Cursor.HAND);
                    text.setStyle("-fx-font-size: 15px");
                    label.setGraphic(text);
                    label.setGraphicTextGap(10);
                    label.setContentDisplay(ContentDisplay.LEFT);
                    headerController.getBreadcrumbs().getChildren().add(label);
                    HBox.setMargin(label, new Insets(0, 0, 0, 10));
                }

                @Override
                public void removeAddress(Integer number) {
                    HBox breadcrumbs = (HBox) headerController.getBreadcrumbs();
                    if(breadcrumbs.getChildren().size() - number >= 3) {
                        while (number>0) {
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
            });
            headerController.setScreenListener(new NewScreenListener() {
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
            courseListController.setHeaderListener(new HeaderListener() {
                @Override
                public void handle(Event event) {

                }

                @Override
                public void addAddressToBreadcrumbs(String address) {
                    Label label = new Label(address);
                    Text text = new Text("/");
                    label.setStyle("-fx-font-size: 15px; -fx-text-fill: #c53624;");
                    label.setCursor(Cursor.HAND);
                    text.setStyle("-fx-font-size: 15px");
                    label.setGraphic(text);
                    label.setGraphicTextGap(10);
                    label.setContentDisplay(ContentDisplay.LEFT);
                    headerController2.getBreadcrumbs().getChildren().add(label);
                    HBox.setMargin(label, new Insets(0, 0, 0, 10));
                }

                @Override
                public void removeAddress(Integer number) {
                    HBox breadcrumbs = (HBox) headerController2.getBreadcrumbs();
                    if(breadcrumbs.getChildren().size() - number >= 3) {
                        while (number>0) {
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
            });
            courseListController.setScreenListener(new NewScreenListener() {
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
        setHeader();
        addQuizList();
    }
}
