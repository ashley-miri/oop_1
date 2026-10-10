import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    //data fields
    private int nextOrderId = 1;
    private final List<Order> orders = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>(); //db.loadCouriers();

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

    //functions
    private int setOrderId() {
        return nextOrderId++;
    }

    private void releaseCourier(Order order) {
        for (Courier courier : couriers) {
            if (courier.getId().equals(order.getCourierId())) {
                order.setCourierId(null);
                courier.setBusy(false);
            }
        }
    }

    private Courier findFreeCourier(TypeDelivery type) {
        for (Courier courier : couriers) {
            if (!courier.isBusy() && courier.getType() == type) {
                return courier;
            }
        }
        return null;
    }

    private boolean tryAssignCourier(Order order, TypeDelivery type) {
        Courier courier = findFreeCourier(type);
        if (courier == null) {
            return false;
        } else {
            order.setStatus(OrderStatus.ASSIGNED);
            order.setCourierId(courier.getId());
            courier.setBusy(true);
            return true;
        }
    }

    public boolean assignCourier(Order order, TypeDelivery type) {
        if (type == null) {
            return false;
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            releaseCourier(order);
            order.setStatus(OrderStatus.CREATED);
        }
        if (type == TypeDelivery.SELF_PICKUP) {
            order.setStatus(OrderStatus.SELF_PICKUP);
            return true;
        }
        if (tryAssignCourier(order, type)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tryAddProduct(Order order, Product product) {
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            return false;
        }
        order.addItem(product);
        return true;
    }
}
