package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static program.Helper.readInputInt;

public class CategoryCrud {
    static Scanner in = new Scanner(System.in);

    public static void ShowCategory(List<Category> category) {
        for (Category c : category) {
            System.out.println(c.toString());
        }
    }

    public static void InsertCategoryIntoDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Вдале з'єднання з сервером");
            String query = "INSERT INTO `category` (`categoryName`)" + "VALUES (?);";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String categoryName;
                System.out.println("Введіть назву категорії: ");
                categoryName = in.nextLine();

                stmt.setString(1, categoryName);
                int rows = stmt.executeUpdate();
                System.out.println("Оновлено рядків: " + rows);

            } catch (Exception ex) {
                System.out.println("Помилка запиту: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }

    public static List<Category> SelectCategoryFromDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String selectSql = "SELECT * FROM category";
            try {
                PreparedStatement ps = con.prepareStatement(selectSql);
                ResultSet resultSet = ps.executeQuery();
                List<Category> category = new ArrayList<>();
                while (resultSet.next()) {
                    Category c = new Category(
                            resultSet.getInt("id"),
                            resultSet.getString("categoryName"));
                    category.add(c);
                }
                return category;
            } catch (Exception ex) {
                System.out.println("Помилка виконання запиту: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
        return null;
    }

    public static void UpdateCategoryForDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String query = "UPDATE category SET categoryName = ? WHERE Id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("\nВведіть id категорії: ");
                int id = readInputInt();
                System.out.print("Введіть нову назву категорії: ");
                String categoryName = in.nextLine();

                stmt.setString(1, categoryName);
                stmt.setInt(2, id);

                int rows = stmt.executeUpdate();

                System.out.println("Успішно оновлено рядків таблиці: " + rows);
            } catch (Exception ex) {
                System.out.println("Помилка оновлення:" + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }


    public static void DeleteCategoryFromDB(String strConn) {
        SelectCategoryFromDB(strConn);
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String query = "DELETE FROM category WHERE id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("Введіть Id категорії: ");
                int id = readInputInt();
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                System.out.println("Успішно видалено рядків: " + rows);

            } catch (Exception ex) {
                System.out.println("Помилка видалення: " + ex.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }
}
