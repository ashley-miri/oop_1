import java.util.List;
import java.util.Scanner;

public class ConsoleIO {

    private static final Scanner scan = new Scanner(System.in);

    public static int readInt(String promt) {
        while (true) {
            outText(promt);
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                outText("You need to enter an integer.");
            }
        }
    }

    public static String readStr(String promt) {
        while (true) {
            outText(promt);
            String str = scan.nextLine().trim();
            if (!str.isEmpty()) {
                return str;
            } else {
                outText("Enter a non-empty string.");
            }
        }
    }

    public static void outText(String text) {
        System.out.print("\n" + text);
    }

    public static <T> T chooseFromList(List<T> list) {
        if (list.isEmpty()) {
            outText("List is empty.");
            return null;
        }

        int page = 0;
        while (true) {
            for (int i = 1; i <= 5; i++) {
                if (5 * page + i - 1 > list.size() - 1) {
                    break;
                }
                outText(i + ") " + list.get(5 * page + i - 1).toString());
            }
            outText("6)Предыдущая страница\n7)Следующая страница\n8)Выход");
            int r = readInt("Ввод: ");
            switch (r) {
                case 1, 2, 3, 4, 5:
                    if (5 * page + r - 1 > list.size() - 1) {
                        outText("Некорректное значение, повторите ввод");
                        continue;
                    }
                    return list.get(5 * page + r - 1);
                case 6:
                    if (page == 0) {
                        outText(
                            "Это первая страница, предыдущей не существует"
                        );
                        break;
                    }
                    page--;
                    break;
                case 7:
                    if ((list.size() + 4) / 5 == page + 1) {
                        outText(
                            "Это последняя страница, последущей не существует"
                        );
                        break;
                    }
                    page++;
                    break;
                case 8:
                    return null;
                default:
                    outText("Некорректное значение, повторите ввод");
                    break;
            }
        }
    }

    public static TypeDelivery chooseTypeDelivery(OrderStatus status) {
        if (
            status != OrderStatus.ASSIGNED &&
            status != OrderStatus.SELF_PICKUP &&
            status != OrderStatus.CREATED
        ) {
            outText("Заказ передан в доставку или уже доставлен");
            return null;
        }
        if (
            status == OrderStatus.ASSIGNED || status == OrderStatus.SELF_PICKUP
        ) {
            if (!takeWriteAccess("изменить тип доставки")) {
                return null;
            }
        }
        outText("Выберете тип доствки:\n1)Обычная\n2)Скоростная\n3)Самовывоз");
        while (true) {
            int i = readInt("Enter: ");
            switch (i) {
                case 1:
                    return TypeDelivery.ORDINARY;
                case 2:
                    return TypeDelivery.EXPRESS;
                case 3:
                    return TypeDelivery.SELF_PICKUP;
                default:
                    ConsoleIO.outText(
                        "Некорректное значение повторите ввод числа от 1 до 3"
                    );
            }
        }
    }

    public static boolean takeWriteAccess(String text) {
        while (true) {
            outText("Вы уверены что хотите " + text + " ?\n1)ДА\n2)НЕТ");
            int i = ConsoleIO.readInt("Enter: ");
            switch (i) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    ConsoleIO.outText(
                        "Неверное значение, введите число 1 или 2"
                    );
                    break;
            }
        }
    }
}
