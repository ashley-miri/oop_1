import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DataBase data = new DataBase();
        try {
            data.init();
            new DataSeeder(data).seedIfEmpty();
            DeliveryService service = new DeliveryService(data.loadCouriers());
            AuthService auth = new AuthService(data);
            Menu menu = new Menu(service, data, auth);
            menu.mainMenu();
        } catch (SQLException e) {
            ConsoleIO.outText("Ошибка базы данных " + e.getMessage());
        }
    }
}
