package model2;

import javafx.scene.image.Image;

public class Choice {
    private String options;
    private Image optionImages;
    private Double optionGrades;

    public Choice() {
    }

    public Choice(String options, Image optionImages, Double optionGrades) {
        this.options = options;
        this.optionImages = optionImages;
        this.optionGrades = optionGrades;
    }
    public String getOptions() {
        return options;
    }

    public Image getOptionImages() {
        return optionImages;
    }

    public Double getOptionGrades() {
        return optionGrades;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setOptionImages(Image optionImages) {
        this.optionImages = optionImages;
    }

    public void setOptionGrades(Double optionGrades) {
        this.optionGrades = optionGrades;
    }
}
