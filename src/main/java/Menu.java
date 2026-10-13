import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menu {

    private final DeliveryService service;
    private final DataBase data;
    private final AuthService auth;

    public Menu(DeliveryService service, DataBase data, AuthService auth) {
        this.service = service;
        this.data = data;
        this.auth = auth;
    }

    public void mainMenu() {
        while (true) {
            ConsoleIO.clear();
            ConsoleIO.outText(
                "Выберете:\n1)Вход\n2)Регистрация\n3)Выход из программы"
            );
            int i = ConsoleIO.readInt("Ввод: ");
            switch (i) {
                case 1 -> {
                    AuthUser user = loginMenu();
                    if (user != null) {
                        switch (user.getRole()) {
                            case CLIENT -> clientMenu((Client) user);
                            case ADMIN -> adminMenu((Admin) user);
                        }
                    }
                }
                case 2 -> {
                    newUser();
                }
                case 3 -> {
                    return;
                }
                default -> {
                    ConsoleIO.outText(
                        "Некорректное значение выбирите цифру 1 или 2"
                    );
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                    break;
                }
            }
        }
    }

    private AuthUser loginMenu() {
        while (true) {
            ConsoleIO.clear();
            String login = ConsoleIO.readStr("Введите логин: ");
            String passwd = ConsoleIO.readStr("Введите пароль: ");
            try {
                AuthUser user = auth.tryLogin(login, passwd);
                if (user != null) {
                    ConsoleIO.outText("Вход успешен");
                    return user;
                }
                ConsoleIO.outText(
                    "Неправильный логин или пароль, повторите попытку, если желаете выйти введите ВЫХОД, если хотите продолжить введите люуую непустую строку"
                );
                if (ConsoleIO.readStr("Ввод: ").equalsIgnoreCase("ВЫХОД")) {
                    return null;
                }
            } catch (SQLException e) {
                ConsoleIO.outText("Ошибка базы данных " + e.getMessage());
                return null;
            }
        }
    }

    private void clientMenu(Client client) {
        while (true) {
            ConsoleIO.clear();
            ConsoleIO.outText(
                "Меню клиента:\n" +
                    "1)Новый заказ\n" +
                    "2)Мои заказы\n" +
                    "3)Изменить способ доставки\n" +
                    "4)Отменить заказ\n" +
                    "5)Выйти из аккаунта"
            );
            switch (ConsoleIO.readInt("Ввод: ")) {
                case 1 -> createOrder(client);
                case 2 -> showClientOrders(client);
                case 3 -> changeDelivery(client);
                case 4 -> cancelOrder(client);
                case 5 -> {
                    return;
                }
                default -> {
                    ConsoleIO.outText(
                        "Некорректное значение, введите число от 1 до 5"
                    );
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
            }
        }
    }

    private void adminMenu(Admin admin) {
        while (true) {
            ConsoleIO.clear();
            ConsoleIO.outText(
                "Меню администратора:\n" +
                    "1)Все заказы\n" +
                    "2)Изменить статус заказа\n" +
                    "3)Список курьеров\n" +
                    "4)Добавить курьера\n" +
                    "5)Добавить товар\n" +
                    "6)Добавить администратора\n" +
                    "7)Выйти из аккаунта"
            );
            switch (ConsoleIO.readInt("Ввод: ")) {
                case 1 -> showAllOrders();
                case 2 -> changeOrderStatus();
                case 3 -> showCouriers();
                case 4 -> addCourier();
                case 5 -> addProduct();
                case 6 -> addAdmin();
                case 7 -> {
                    return;
                }
                default -> {
                    ConsoleIO.outText(
                        "Некорректное значение, введите число от 1 до 8"
                    );
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
            }
        }
    }

    private String readFreeLogin() throws SQLException {
        String login = ConsoleIO.readStr("Логин: ");
        while (data.loginExists(login)) {
            ConsoleIO.outText("Такой логин уже занят");
            if (!ConsoleIO.takeWriteAccess("ввести другой логин")) {
                return null;
            }
            login = ConsoleIO.readStr("Логин: ");
        }
        return login;
    }

    private void newUser() {
        ConsoleIO.clear();
        String name = ConsoleIO.readStr("Введите фио: ");
        String phone = ConsoleIO.readStr("Телефон: ");
        String address = ConsoleIO.readStr("Адрес: ");
        try {
            String login = readFreeLogin();
            if (login == null) {
                return;
            }
            String password = ConsoleIO.readStr("Пароль: ");

            Client newClient = new Client(
                UUID.randomUUID(),
                name,
                phone,
                address,
                login,
                AuthUser.hashPassword(password)
            );
            data.saveClient(newClient);
            ConsoleIO.outText(
                "Регистрация прошла успешно, теперь войдите под своим логином: " +
                    login
            );
        } catch (SQLException e) {
            ConsoleIO.outText("Ошибка базы данных: " + e.getMessage());
        }
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void createOrder(Client client) {
        Order order = service.createOrder(client.getId());
        boolean pick = true;
        while (pick) {
            ConsoleIO.clear();
            List<Product> items = order.getItems();
            ConsoleIO.outText(
                "1)Добавить товар\n" +
                    "2)Просмотр корзины\n" +
                    "3)Убрать товар\n" +
                    "4)Перейти к доставке\n" +
                    "5)Отменить оформление"
            );
            switch (ConsoleIO.readInt("Ввод: ")) {
                case 1 -> {
                    String query = ConsoleIO.readStr(
                        "Название товара (или его часть): "
                    );
                    try {
                        List<Product> found = data.findProduct(query);
                        if (found.isEmpty()) {
                            ConsoleIO.outText(
                                "По запросу «" + query + "» ничего не найдено"
                            );
                        } else {
                            Product product = ConsoleIO.chooseFromList(found);
                            if (product != null) {
                                service.tryAddProduct(order, product);
                                ConsoleIO.outText("Добавлено: " + product);
                            }
                        }
                    } catch (SQLException e) {
                        ConsoleIO.outText(
                            "Ошибка базы данных: " + e.getMessage()
                        );
                    }
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
                case 2 -> {
                    if (items.isEmpty()) {
                        ConsoleIO.outText("Ваш заказ пока пуст");
                    } else {
                        ConsoleIO.showList(items);
                    }
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
                case 3 -> {
                    if (items.isEmpty()) {
                        ConsoleIO.outText("Убирать нечего, заказ пуст");
                    } else {
                        Product product = ConsoleIO.chooseFromList(items);
                        if (product != null) {
                            order.removeItem(product);
                            ConsoleIO.outText("Убрано: " + product);
                        }
                    }
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
                case 4 -> {
                    if (items.isEmpty()) {
                        ConsoleIO.outText("Добавьте хотя бы один товар");
                    } else {
                        pick = false;
                    }
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
                case 5 -> {
                    ConsoleIO.outText("Оформление отменено");
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                    return;
                }
                default -> {
                    ConsoleIO.outText(
                        "Некорректное значение, введите число от 1 до 5"
                    );
                    ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
                }
            }
        }

        TypeDelivery type = ConsoleIO.chooseTypeDelivery(order.getStatus());
        if (service.assignCourier(order, type)) {
            ConsoleIO.outText(
                "Итого: " +
                    (order.getCost() + type.getPrice()) +
                    " руб. где " +
                    type.getPrice() +
                    " руб. за доставку "
            );
        } else {
            ConsoleIO.outText(
                "Заказ №" +
                    order.getId() +
                    " оформлен. Свободных курьеров сейчас нет, заказ ожидает назначения\n" +
                    "Итого: " +
                    (order.getCost() + type.getPrice()) +
                    " руб. где " +
                    type.getPrice() +
                    " руб. за доставку "
            );
        }
        service.addOrder(order);
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void showClientOrders(Client client) {
        ConsoleIO.clear();
        List<Order> result = service.findAllClientOrders(client.getId());
        ConsoleIO.showList(result);
    }

    private void changeDelivery(Client client) {
        ConsoleIO.clear();
        List<Order> result = service.findAllClientOrders(client.getId());
        Order order = ConsoleIO.chooseFromList(result);
        if (order == null) {
            return;
        }
        TypeDelivery type = ConsoleIO.chooseTypeDelivery(order.getStatus());
        if (type == null) {
            return;
        }
        if (service.assignCourier(order, type)) {
            ConsoleIO.outText("Способ доставки изменён: " + type.getLabel());
        } else {
            ConsoleIO.outText(
                "Свободных курьеров сейчас нет, заказ ожидает назначения"
            );
        }
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void cancelOrder(Client client) {
        ConsoleIO.clear();
        List<Order> result = service.findAllClientOrders(client.getId());
        Order order = ConsoleIO.chooseFromList(result);
        if (
            order == null ||
            !order.getStatus().canTransitionTo(OrderStatus.CANCELLED)
        ) {
            ConsoleIO.outText("Уже невозможно отменить заказ");
            ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
            return;
        }
        service.cancelOrder(order);
        ConsoleIO.outText("Заказ успешно отменен");
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void showAllOrders() {
        ConsoleIO.clear();
        ConsoleIO.showList(service.getOrders());
    }

    private void changeOrderStatus() {
        ConsoleIO.clear();
        Order order = ConsoleIO.chooseFromList(service.getOrders());
        if (order == null) {
            return;
        }
        List<OrderStatus> allowed = new ArrayList<>();
        for (OrderStatus next : OrderStatus.values()) {
            if (
                next != OrderStatus.ASSIGNED &&
                order.getStatus().canTransitionTo(next)
            ) {
                allowed.add(next);
            }
        }
        OrderStatus status = ConsoleIO.chooseFromList(allowed);
        if (status == null) {
            return;
        }
        service.changeStatus(order, status);
        ConsoleIO.outText("Статус заказа изменён: " + status.getLabel());
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void showCouriers() {
        ConsoleIO.clear();
        ConsoleIO.chooseFromList(service.getCouriers());
    }

    private void addCourier() {
        ConsoleIO.clear();
        String name = ConsoleIO.readStr("ФИО курьера: ");
        String phone = ConsoleIO.readStr("Телефон: ");

        TypeDelivery type = null;
        while (type == null) {
            ConsoleIO.outText("Тип доставки:\n1)Обычная\n2)Скоростная");
            switch (ConsoleIO.readInt("Ввод: ")) {
                case 1 -> type = TypeDelivery.ORDINARY;
                case 2 -> type = TypeDelivery.EXPRESS;
                default -> ConsoleIO.outText(
                    "Некорректное значение, введите 1 или 2"
                );
            }
        }

        Courier courier = new Courier(UUID.randomUUID(), name, phone, type);
        try {
            //Добавляю в дб раньше чтобы если UID совпаль то программа отъебнула раньше чем курьере попадет в список курьеров с одинаковым UID
            data.saveCourier(courier);
            service.addCourier(courier);
            ConsoleIO.outText("Курьер добавлен: " + courier);
        } catch (SQLException e) {
            ConsoleIO.outText("Ошибка базы данных: " + e.getMessage());
        }
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void addProduct() {
        ConsoleIO.clear();
        String name = ConsoleIO.readStr("Название товара: ");
        int cost = ConsoleIO.readPositiveInt("Цена, руб.: ");
        int weight = ConsoleIO.readPositiveInt("Вес, г: ");

        Product product = new Product(0, name, cost, weight);
        try {
            data.saveProduct(product);
            ConsoleIO.outText("Товар добавлен: " + product);
        } catch (SQLException e) {
            ConsoleIO.outText("Ошибка базы данных: " + e.getMessage());
        }
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }

    private void addAdmin() {
        ConsoleIO.clear();
        String name = ConsoleIO.readStr("ФИО администратора: ");
        String phone = ConsoleIO.readStr("Телефон: ");
        try {
            String login = readFreeLogin();
            if (login == null) {
                return;
            }
            String password = ConsoleIO.readStr("Пароль: ");

            Admin newAdmin = new Admin(
                UUID.randomUUID(),
                name,
                phone,
                login,
                AuthUser.hashPassword(password)
            );
            data.saveAdmin(newAdmin);
            ConsoleIO.outText("Администратор добавлен: " + login);
        } catch (SQLException e) {
            ConsoleIO.outText("Ошибка базы данных: " + e.getMessage());
        }
        ConsoleIO.pause("Нажмите ENTER чтобы продолжить");
    }
}
