package model;

import javafx.scene.image.Image;

public class Choice {
    private String option;
    private Image optionImage;
    private Double optionGrade;

    public Choice() {
    }
    public Choice(String options, Image optionImages, Double optionGrades) {
        this.option = options;
        this.optionImage = optionImages;
        this.optionGrade = optionGrades;
    }
    public String getOption() {
        return option;
    }

    public Image getOptionImage() {
        return optionImage;
    }

    public Double getOptionGrade() {
        return optionGrade;
    }

    public void setOption(String options) {
        this.option = options;
    }

    public void setOptionImage(Image optionImages) {
        this.optionImage = optionImages;
    }

    public void setOptionGrade(Double optionGrades) {
        this.optionGrade = optionGrades;
    }
}
