package model;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class GeneralFunctions {
    public static String getCateName(String nameWithQuantity) {
        if (nameWithQuantity == null) return null;
        String categoryName = nameWithQuantity.replaceAll("^\\s+", "");
        if (categoryName.endsWith(")")) {
            return categoryName.substring(0, categoryName.lastIndexOf('(') - 1);
        }
        return categoryName;
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
