package model;

import java.util.List;

public class Category {
    private String categoryID;
    private String categoryTitle;

    public String getCategoryID() {
        return categoryID;
    }
    public String getCategoryTitle() {
        return categoryTitle;
    }
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    public Category(String CategoryID, String CategoryTitle) {
        this.categoryID = CategoryID;
        this.categoryTitle = CategoryTitle;
    }
    public List<Question> getQuestions() {
        DBInteract dbi = new DBInteract();
        return dbi.getQuestionsBelongToCategory(categoryTitle);
    }
}
