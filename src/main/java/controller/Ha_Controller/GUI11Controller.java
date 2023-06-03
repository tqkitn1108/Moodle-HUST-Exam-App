package controller.Ha_Controller;

import controller.Hung_Controller.GUI61Controller;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import listeners.NewScreenListener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI11Controller implements Initializable {

    @FXML
    private VBox header;
    @FXML
    private StackPane stackPane;
//    @FXML
//    private MenuButton menuWindow;
//    @FXML
//    private MFXButton editingBtn;
//    @FXML
//    private VBox listBtn;
//
//    public void hideEditingBtn() {
//        editingBtn.setVisible(false);
//    }
//
//    public void showEditingBtn() {
//        editingBtn.setVisible(true);
//    }

    @FXML
    private void backGUI11(MouseEvent event) {
        addQuizList();
    }

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
    @FXML
    private void viewQuestion(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.hide();
//            menuWindow.setVisible(false);
//            hideEditingBtn();
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
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCategory(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.hide();
//            menuWindow.setVisible(false);
//            hideEditingBtn();
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
            gui21Controller.getTabPane().getSelectionModel().select(1);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openImport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.hide();
//            menuWindow.setVisible(false);
//            hideEditingBtn();
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
            gui21Controller.getTabPane().getSelectionModel().select(2);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openExport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI21.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.hide();
//            menuWindow.setVisible(false);
//            hideEditingBtn();
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
            gui21Controller.getTabPane().getSelectionModel().select(3);
            gui21Controller.getTabPane().getSelectionModel().getSelectedItem().getContent().toFront();
            stackPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openEditing(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/GUI51.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.hide();
            GUI51Controller gui51Controller = fxmlLoader.getController();
            gui51Controller.setScreenListener(new NewScreenListener() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHeader(Node node) {
        this.header.getChildren().add(node);
    }

    private void addScreenToStackPane(Node node) {
        this.stackPane.getChildren().add(node);
    }

    private void setHeader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HA_FXML/Header.fxml"));
            Node node = fxmlLoader.load();
//            menuWindow.setVisible(true);
//            showEditingBtn();
            HeaderController headerController = fxmlLoader.getController();
            headerController.setHeaderListener(new NewScreenListener() {
                @Override
                public void changeScreen(Node node) {
                    addHeader(node);
                }

                @Override
                public void removeTopScreen() {
                    header.getChildren().remove(header.getChildren().size() - 1);
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
