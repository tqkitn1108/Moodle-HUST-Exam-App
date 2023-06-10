package model;

public class Category {
    private String categoryID;
    private String categoryTitle;

    public String getCategoryID() {
        return categoryID;
    }
    public String getCatTitle() {
        return categoryTitle;
    }
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    Category(String CategoryID, String CategoryTitle) {
        this.categoryID = CategoryID;
        this.categoryTitle = CategoryTitle;
    }

}
