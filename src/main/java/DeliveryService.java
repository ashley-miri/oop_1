import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeliveryService {

    //data fields
    private int nextOrderId = 1;
    private final List<Order> orders = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();

    public DeliveryService(List<Courier> couriersFromDb) {
        couriers.addAll(couriersFromDb);
    }

    //getters
    public List<Order> getOrders() {
        return orders;
    }

    public List<Courier> getCouriers() {
        return couriers;
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

    //functions
    public Order createOrder(UUID clientId) {
        return new Order(nextOrderId++, clientId);
    }

    public Courier findCourier(UUID id) {
        if (id == null) {
            return null;
        }
        for (Courier courier : couriers) {
            if (courier.getId().equals(id)) {
                return courier;
            }
        }
        return null;
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

    private boolean tryAssignCourier(Order order) {
        Courier courier = findFreeCourier(order.getTypeDelivery());
        if (courier == null) {
            return false;
        }
        order.setStatus(OrderStatus.ASSIGNED);
        order.setCourierId(courier.getId());
        courier.setBusy(true);
        return true;
    }

    public boolean assignCourier(Order order, TypeDelivery type) {
        if (type == null) {
            return false;
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            order.setStatus(OrderStatus.CREATED);
            releaseCourier(order);
        }
        order.setTypeDelivery(type);
        if (type == TypeDelivery.SELF_PICKUP) {
            order.setStatus(OrderStatus.SELF_PICKUP);
            return true;
        }
        return tryAssignCourier(order);
    }

    public boolean tryAddProduct(Order order, Product product) {
        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            return false;
        }
        order.addItem(product);
        return true;
    }

    public void changeStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        if (
            status == OrderStatus.DELIVERED ||
            status == OrderStatus.CANCELLED ||
            status == OrderStatus.CREATED
        ) {
            releaseCourier(order);
        }
    }

    public List<Order> findAllClientOrders(UUID clientId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (clientId.equals(order.getClientId())) {
                result.add(order);
            }
        }
        return result;
    }

    public void cancelOrder(Order order) {
        releaseCourier(order);
        order.setStatus(OrderStatus.CANCELLED);
    }
}
