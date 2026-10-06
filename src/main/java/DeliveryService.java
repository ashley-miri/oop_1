import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    //data fields
    private final List<Order> orders = new ArrayList<>();

    //getters
    public List<Order> getOrders() {
        return orders;
    }

    //setters
    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }
}

    //functions
    private void setOrderCourier(Order order, Courier courier) {
        if (courier.isBusy()) {
            throw new BusyCourierException(
                "Courier id " + courier.getId() + " is busy now"
            );
        }
        order.setCourierId(courier.getId());
        courier.setBusy(true);
    }

    private void completeOrder(Order order, Courier courier) {
        if (order.getCourierId() != courier.getId()) {
            throw new BusyCourierException(
                "This courier is not involved with this order"
            );
        }
        courier.setBusy(false);
    }

    public static void orderProcessing() {}
}
