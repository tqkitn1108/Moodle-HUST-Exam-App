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
    public String getOption() {
        return options;
    }

    public Image getOptionImage() {
        return optionImages;
    }

    public Double getOptionGrade() {
        return optionGrades;
    }

    public void setOption(String options) {
        this.options = options;
    }

    public void setOptionImage(Image optionImages) {
        this.optionImages = optionImages;
    }

    public void setOptionGrade(Double optionGrades) {
        this.optionGrades = optionGrades;
    }
}
