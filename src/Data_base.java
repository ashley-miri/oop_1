import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Input_data {

    //functions
    public static void read_products(String path, Delivery_service main_data) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] part = line.split("/");
                String name = part[0].trim();
                int cost = Integer.parseInt(part[1].trim());
                int weight = Integer.parseInt(part[2].trim());
                main_data.add_product(new Product(name, cost, weight));
            }
        } catch (IOException e) {
            System.out.println(
                "Не удалось открыть или прочитать файл: " + e.getMessage()
            );
        }
    }

    public static void read_couriers(String path, Delivery_service main_data) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] part = line.split("/");
                int id = Integer.parseInt(part[0].trim());
                String type_delivery = part[1].trim();
                main_data.add_courier(new Courier(id, type_delivery));
            }
        } catch (IOException e) {
            System.out.println(
                "Не удалось открыть или прочитать файл: " + e.getMessage()
            );
        }
    }
}
