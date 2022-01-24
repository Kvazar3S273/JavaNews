package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static program.CategoryCrud.InsertCategoryIntoDB;
import static program.CategoryCrud.ShowCategory;
import static program.CategoryCrud.SelectCategoryFromDB;
import static program.CategoryCrud.UpdateCategoryForDB;
import static program.CategoryCrud.DeleteCategoryFromDB;


import static program.NewsCrud.ShowNews;
import static program.NewsCrud.InsertNewsIntoDB;
import static program.NewsCrud.SelectNewsFromDB;
import static program.NewsCrud.UpdateNewsForDB;
import static program.NewsCrud.DeleteNewsFromDB;


public class Main {

    public static void main(String[] args) {
        String strConn = "jdbc:mariadb://localhost:3306/javadb";

//        InsertCategoryIntoDB(strConn);
//        List<Category> categoryList = SelectCategoryFromDB(strConn);
//        ShowCategory(categoryList);
//        UpdateCategoryForDB(strConn);
//        DeleteCategoryFromDB(strConn);
//
//        InsertNewsIntoDB(strConn);
        List<News> newsList = SelectNewsFromDB(strConn);
        ShowNews(newsList);
//        UpdateNewsForDB(strConn);
//        DeleteNewsFromDB(strConn);
    }






}
