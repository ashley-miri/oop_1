import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class DataSeeder {

    private final DataBase data;

    public DataSeeder(DataBase data) {
        this.data = data;
    }

    public void seedIfEmpty() throws SQLException {
        if (data.hasNoAdmins()) {
            seedAdmin();
        }
        if (data.hasNoCouriers()) {
            seedCouriers();
        }
        if (data.hasNoProducts()) {
            seedProducts();
        }
    }

    private void seedAdmin() throws SQLException {
        data.saveAdmin(
            new Admin(
                UUID.randomUUID(),
                "Главный администратор",
                "+7 900 000-00-00",
                "admin",
                AuthUser.hashPassword("admin")
            )
        );
        ConsoleIO.outText(
            "Создан администратор по умолчанию: логин admin, пароль admin"
        );
    }

    private void seedCouriers() throws SQLException {
        List<Courier> couriers = List.of(
            new Courier(
                UUID.randomUUID(),
                "Иванов Иван Иванович",
                "+7 911 111-11-11",
                TypeDelivery.ORDINARY
            ),
            new Courier(
                UUID.randomUUID(),
                "Петров Пётр Петрович",
                "+7 911 222-22-22",
                TypeDelivery.ORDINARY
            ),
            new Courier(
                UUID.randomUUID(),
                "Смирнова Анна Сергеевна",
                "+7 911 333-33-33",
                TypeDelivery.EXPRESS
            ),
            new Courier(
                UUID.randomUUID(),
                "Кузнецов Алексей Олегович",
                "+7 911 444-44-44",
                TypeDelivery.EXPRESS
            )
        );
        for (Courier courier : couriers) {
            data.saveCourier(courier);
        }
    }

    private void seedProducts() throws SQLException {
        // id = 0: настоящий id назначит SQLite (INTEGER PRIMARY KEY)
        List<Product> products = List.of(
            new Product(0, "Хлеб белый", 55, 400),
            new Product(0, "Хлеб ржаной", 60, 500),
            new Product(0, "Молоко 3,2%", 95, 1000),
            new Product(0, "Кефир 1%", 85, 900),
            new Product(0, "Сыр российский", 320, 300),
            new Product(0, "Масло сливочное", 210, 180),
            new Product(0, "Яйца куриные, 10 шт", 120, 600),
            new Product(0, "Гречка", 110, 900),
            new Product(0, "Рис круглозёрный", 130, 900),
            new Product(0, "Макароны", 90, 450),
            new Product(0, "Куриное филе", 380, 1000),
            new Product(0, "Фарш говяжий", 450, 500),
            new Product(0, "Яблоки", 140, 1000),
            new Product(0, "Бананы", 160, 1000),
            new Product(0, "Картофель", 70, 2000),
            new Product(0, "Помидоры", 250, 1000),
            new Product(0, "Огурцы", 180, 1000),
            new Product(0, "Чай чёрный", 230, 100),
            new Product(0, "Кофе молотый", 490, 250),
            new Product(0, "Шоколад молочный", 110, 90)
        );
        for (Product product : products) {
            data.saveProduct(product);
        }
    }
}
