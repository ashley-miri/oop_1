import java.util.Scanner;

public class ConsoleIO {

    private static final Scanner scan = new Scanner(System.in);

    public static int readInt(String promt) {
        while (true) {
            System.out.print(promt);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число.");
            }
        }
    }

    public static void outText(String text) {
        System.out.print("\n" + text);
    }
}
