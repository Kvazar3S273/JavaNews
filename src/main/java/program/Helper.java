package program;

import java.util.Scanner;

public class Helper {
    static Scanner in = new Scanner(System.in);

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
