package model;

public class test {
    public static void main(String[] args) throws Exception {
        DBInteract dbi = new DBInteract();

//        dbi.deleteCategory("testCat");
        dbi.deleteCategory("CNXH");
        dbi.deleteQuiz("CNXH_GK_1");
        dbi.deleteQuiz("CNXH_GK_2");
//        List<Question> questions = dbi.getQuestionBelongToQuiz("CNXH_GK_1");
//        for (Question q : questions) {
//            q.showQ();
//        }
    }
}
