package model;

public class test {
    public static void main(String[] args) throws Exception {
        DBInteract dbi = new DBInteract();
        dbi.deleteCategory("CNXH");
        dbi.deleteQuiz("CNXH_GK_1");
        dbi.deleteQuiz("CNXH_GK_2");
    }

}
