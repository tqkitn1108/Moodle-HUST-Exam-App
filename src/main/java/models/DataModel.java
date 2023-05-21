package models;

import java.util.Map;

public class DataModel {

    private static DataModel instance;

    private Integer number;

    private Map<Integer, Integer> userAnswer;

    private DataModel() {

    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setUserAnswer(Map<Integer, Integer> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Map<Integer, Integer> getUserAnswer() {
        return userAnswer;
    }
}
