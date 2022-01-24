package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String strConn = "jdbc:mariadb://localhost:3306/javadb";
    }

    private static void ShowNews(List<News> news) {
        for (News n : news) {
            System.out.println(n.toString());
        }
    }

    private static void InsertIntoDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Вдале з'єднання з сервером");
            String query = "INSERT INTO `news` (`title`,`text`)" + "VALUES (?,?);";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String title, text;
                System.out.println("Введіть заголовок новини: ");
                title = in.nextLine();
                System.out.println("Введіть текст новини: ");
                text = in.nextLine();
                stmt.setString(1, title);
                stmt.setString(2, text);
                int rows = stmt.executeUpdate();
                System.out.println("Оновлено рядків: " + rows);

            } catch (Exception ex) {
                System.out.println("Помилка запиту: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Помилка з'єднання: " + ex.getMessage());
        }
    }

    private static List<News> SelectFromDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String selectSql = "SELECT * FROM news";
            try {
                PreparedStatement ps = con.prepareStatement(selectSql);
                ResultSet resultSet = ps.executeQuery();
                List<News> news = new ArrayList<>();
                while (resultSet.next()) {
                    News n = new News(resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("text"));
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

    private static void UpdateForDB(String strConn) {
        try (Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String query = "UPDATE news SET title = ? WHERE id = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("\nВведіть id новини: ");
                int id = readInputInt();
                System.out.print("Введіть новий заголовок: ");
                String title = in.nextLine();
                stmt.setString(1, title);
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


    private static void DeleteFromDB(String strConn) {
        SelectFromDB(strConn);
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

    // Провіряємо чи введений символ - ціле число
    public static int readInputInt() {
        int result;
        while (true) {
            if (in.hasNextInt()) {
                result = in.nextInt();
                return result;
            } else {
                in.next();
                System.out.println("Це не цифра, введіть ще раз:");
            }
        }
    }






}
