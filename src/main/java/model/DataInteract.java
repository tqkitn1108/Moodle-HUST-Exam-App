package model;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.sqlite.SQLiteConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class DataInteract {
    public static void main(String[] args) {
        try {
//            List<Question> questions = getQuestionsFromDocFile("src/Doc1.docx");
//            //Category cat = new Category("1", "testQuiz");
            DBInteract dbi = new DBInteract();
//            dbi.createNewCategory(null, "1","testCat");
//            dbi.createNewCategory("testCat","2","testSubCat");
//
//            assert questions != null;
//            for (Question q : questions) {
//                dbi.insertQuestion(q,"testCat");
//            }
//
//            questions = dbi.getQuestionsBelongToCategory("testCat");
//
//            Quiz quiz = new Quiz();
//            quiz.setQuizName("testQuiz");
//            quiz.setQuizDescription("just a normal quiz");
//            quiz.setTimeLimit(60);
//
//            dbi.createNewQuiz(quiz);
//            dbi.addQuestionToQuiz("testQuiz","C1D1");
//            dbi.addQuestionToQuiz("testQuiz","C2D2");
//            questions = dbi.getQuestionBelongToQuiz("testQuiz");
//            for (Question q:questions) {
//                q.showQ();
//            }
//            dbi.createNewCategory("testCat","3","anotherSubCat");
//            dbi.createNewCategory(null,"4","anotherCat");
            List<Category> categories = dbi.getAllNonSubCategories();
            for (Category c: categories) {
                System.out.println(c.getCategoryID());
            }


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static List<Question> getQuestionsFromDocFile(String path) {
        try (FileInputStream fis = new FileInputStream(path);
             XWPFDocument doc = new XWPFDocument(fis);) {

            List<String> lines = new ArrayList<>();
            List<Image> images = new ArrayList<>();

            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                String line = "";
                Image image = null;
                for (XWPFRun run : runs) {
                    if (run.getText(0) != null) line = line + run.getText(0);
                    List<XWPFPicture> pictures = run.getEmbeddedPictures();
                    if (pictures.size() > 0) {
                        byte[] pictureByte = pictures.get(0).getPictureData().getData();
                        image = new Image(new ByteArrayInputStream(pictureByte));
                    }
                }
                lines.add(line);
                images.add(image);
            }
            doc.close();

            if (!checkAikenFormat(lines)) return null;
            List<Question> questions = new ArrayList<>();
            int n = lines.size();
            int i = 0;

            do {
                while (i < n && (lines.get(i).equals("") || lines.get(i).equals("null"))) i++;
                if (i == n) break;
                Question q = new Question();

                int index = lines.get(i).indexOf(':');
                q.setQuestionName(lines.get(i).substring(0, index));
                q.setQuestionText(lines.get(i).substring(index + 2));

                if (images.get(i) != null) q.setQuestionImage(images.get(i));

                List<String> options = new ArrayList<String>();
                List<Image> optionImages = new ArrayList<>();
                while (++i < n && (lines.get(i) != null && !lines.get(i).equals(""))) {
                    options.add(lines.get(i));
                    optionImages.add(images.get(i));
                }
                q.setOptionImages(optionImages);
                List<Character> ans = new ArrayList<>();
                String s = options.get(options.size() - 1);
                for (int j = 8; j < s.length(); j++) {
                    if (s.charAt(j) != ' ' && s.charAt(j) != ',') ans.add(s.charAt(j));
                }
                q.setAns(ans);
                options.remove(options.size() - 1);
                q.setOptions(options);

                questions.add(q);

            } while (i < n);
            return questions;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Question> getQuestionsFromTxtFile(String path) {
        List<String> lines = new ArrayList<>();
        try (FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            if (!checkAikenFormat(lines)) return null;
            List<Question> questions = new ArrayList<>();
            int n = lines.size();
            int i = 0;

            do {
                while (i < n && (lines.get(i).equals("") || lines.get(i).equals("null"))) i++;
                if (i == n) break;
                Question q = new Question();

                int index = lines.get(i).indexOf(':');
                q.setQuestionName(lines.get(i).substring(0, index));
                q.setQuestionText(lines.get(i).substring(index + 2));
                q.setQuestionImage(null);

                List<String> options = new ArrayList<String>();
                List<Image> optionImages = new ArrayList<>();
                while (++i < n && (lines.get(i) != null && !lines.get(i).equals(""))) {
                    options.add(lines.get(i));
                    optionImages.add(null);
                }
                q.setOptionImages(optionImages);

                List<Character> ans = new ArrayList<>();
                String s = options.get(options.size() - 1);
                for (int j = 8; j < s.length(); j++) {
                    if (s.charAt(j) != ' ' && s.charAt(j) != ',') ans.add(s.charAt(j));
                }
                q.setAns(ans);
                options.remove(options.size() - 1);
                q.setOptions(options);

                questions.add(q);

            } while (i < n);
            return questions;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static byte[] changeImageToBytes(Image img) throws IOException {
        if (img == null) return null;
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    public static Image changeBytesToImage(byte[] bytes) {
        return new Image(new ByteArrayInputStream(bytes));
    }

    public static Boolean checkAikenFormat(List<String> lines) {
        int n = lines.size();
        int i = 0;

        do {
            while (i < n && (lines.get(i).equals("") || lines.get(i).equals("null"))) i++;
            if (i == n) break;

            if (!lines.get(i).contains(": ")) {
                System.out.println("Error at line " + i + ": Question does not have name");
                return false;
            }

            List<Character> optionLabels = new ArrayList<>();
            while (++i < n && !lines.get(i).equals("null") && !lines.get(i).equals("")) {
                if (lines.get(i).indexOf(". ") != 1 && lines.get(i).indexOf(": ") != 6) {
                    System.out.println("Error at line " + (i+1) + ": Answer label error");
                    return false;
                }
                optionLabels.add(lines.get(i).charAt(0));
            }
            if (optionLabels.size() <= 2) {
                System.out.println("Error at line " + (i-1) + ": Not enough option");
            }
            optionLabels.remove(optionLabels.size()-1);
            String s = lines.get(i-1);
            if (s.indexOf("ANSWER: ") != 0) {
                System.out.println("Error at line " + i + ": Question does not have answer");
            }
            for (int j = 8; j < s.length(); j++) {
                if (s.charAt(j) != ' ' && s.charAt(j) != ',') {
                    if (!optionLabels.contains(s.charAt(j))) {
                        System.out.println("Error at line " + i + ": Answer not in options");
                    }
                }
            }
        } while (i < n);
        return true;
    }
}
