package model;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class test {
    public static void main(String[] args) throws SQLException, IOException {
        DBInteract dbi = new DBInteract();
        List<Category> categories = dbi.getAllNonSubCategories();
        for (Category cat:categories) {
            dbi.treeView(cat.getCatTitle(),0);
        }
    }


}
