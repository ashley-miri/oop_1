import java.util.ArrayList;
import java.util.List;

public class Delivery_service {

    //data fields
    private final List<Order> orders = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    //getters
    public List<Order> get_orders() {
        return orders;
    }

    public List<Courier> get_couriers() {
        return couriers;
    }

    public List<Product> get_products() {
        return products;
    }

    //setters
    public void add_order(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public void add_courier(Courier courier) {
        if (courier != null) {
            couriers.add(courier);
        }
    }

    public void add_product(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    //functions
    public static void order_processing() {}
}
