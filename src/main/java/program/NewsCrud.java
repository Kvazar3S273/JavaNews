package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import program.Main;

import static program.Helper.readInputInt;

public class NewsCrud {
    static Scanner in = new Scanner(System.in);

    public static void ShowNews(List<News> news) {
        for (News n : news) {
            System.out.println(n.toString());
        }
    }

    public static void InsertNewsIntoDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Вдале з'єднання з сервером");
            String query = "INSERT INTO `news` (`title`,`text`,`categoryId`)" + "VALUES (?,?,?);";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String title, text;
                int categoryId;
                System.out.println("Введіть заголовок новини: ");
                title = in.nextLine();
                System.out.println("Введіть текст новини: ");
                text = in.nextLine();

                System.out.print("Ведіть Id категорії, якій відповідає ця новина: ");
                if (in.hasNextInt()) {
                    categoryId = Integer.parseInt(in.nextLine());
                } else {
                    System.out.println("Така категорія відсутня!");
                    return;
                }

                stmt.setString(1, title);
                stmt.setString(2, text);
                stmt.setInt(3, categoryId);
                int rows = stmt.executeUpdate();
                System.out.println("Оновлено рядків: " + rows);

            } catch (Exception ex) {
                System.out.println("Помилка запиту: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }

    public static List<News> SelectNewsFromDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String selectSql = "SELECT * FROM news";
            try {
                PreparedStatement ps = con.prepareStatement(selectSql);
                ResultSet resultSet = ps.executeQuery();
                List<News> news = new ArrayList<>();
                while (resultSet.next()) {
                    News n = new News(resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("text"),
                            resultSet.getInt("categoryId"));
                    news.add(n);
                }
                return news;
            } catch (Exception ex) {
                System.out.println("Помилка виконання запиту: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
        return null;
    }

    public static void UpdateNewsForDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            //String query = "UPDATE news SET title = ? WHERE id = ?";
            String query = "UPDATE news SET title = ?, Text =?, CategoryId=? WHERE Id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("\nВведіть id новини: ");
                int id = readInputInt();
                System.out.print("Введіть новий заголовок: ");
                String title = in.nextLine();
                System.out.print("Введіть новий текст новини: ");
                String text = in.nextLine();
                System.out.print("Ведіть Id категорії, якій відповідає ця новина: ");
                int categoryId = readInputInt();

                stmt.setString(1, title);
                stmt.setString(2, text);
                stmt.setInt(3, categoryId);
                stmt.setInt(4, id);

                int rows = stmt.executeUpdate();

                System.out.println("Успішно оновлено рядків таблиці: " + rows);
            } catch (Exception ex) {
                System.out.println("Помилка оновлення:" + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }


    public static void DeleteNewsFromDB(String strConn) {
        SelectNewsFromDB(strConn);
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String query = "DELETE FROM news WHERE id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("Введіть Id новини: ");
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
