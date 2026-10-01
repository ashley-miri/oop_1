import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    //data fields
    private final List<Order> orders = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    //getters
    public List<Order> getOrders() {
        return orders;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public List<Product> getProducts() {
        return products;
    }

    //setters
    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public void addCourier(Courier courier) {
        if (courier != null) {
            couriers.add(courier);
        }
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    //functions
    public static void orderProcessing() {}
}
