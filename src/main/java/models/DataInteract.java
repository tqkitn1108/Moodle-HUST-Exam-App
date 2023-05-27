package models;

import org.apache.poi.xwpf.usermodel.*;
import org.sqlite.core.DB;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataInteract {
    public static void main(String[] args) {
        List<String> lines = getDataFromDocFiles("src/Doc1.docx");
        List<Question> questions = getQuestionsFromLines(lines);
        Quiz quiz = new Quiz("1", "testquiz");
        DBInteract dbi = new DBInteract();
        dbi.createNewQuiz(quiz);
        for (Question q:questions) {
            q.setQuiz(quiz);
            dbi.insert(q);
        }
        dbi.show();
    }

    public static List<String> getDataFromDocFiles(String path) {
        List<String> lines = new ArrayList<String>();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            XWPFDocument document = new XWPFDocument(fileInputStream);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                //System.out.println(paragraph.getText());
                lines.add(paragraph.getText());
            }

            document.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    public static List<Question> getQuestionsFromLines(List<String> lines) {
        List<Question> questions = new ArrayList<Question>();
        int n = lines.size();
        int i = 0;
        do {
            while (i<n && lines.get(i).equals("")) i++;
            if (i == n) break;
            Question q = new Question();
            //q.quiz = quiz;
            int index = lines.get(i).indexOf(':');
            q.setQuestionID(lines.get(i).substring(0,index));
            q.setQuestionData(lines.get(i).substring(index+2));
            List<String> options = new ArrayList<String>();
            while (++i<n && (lines.get(i) != null && !lines.get(i).equals(""))) {
                options.add(lines.get(i));
                //i++;
            }
            q.setAns(options.get(options.size() - 1).charAt(8));
            options.remove(options.size() - 1);
            q.setOptions(options);
            //q.showQ();
            //iA.insert(q);
            //System.out.println("\n");
            questions.add(q);
        } while (i<n);
        return questions;
    }
}
