import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private void tryAssignCourier(Order order, TypeDelivery type) {
        Courier courier = findFreeCourier(type);
        if (courier == null) {
            ConsoleIO.outText(
                "All couriers for this delivery method are currently busy. Please try again later or change the delivery method."
            );
        } else {
            order.setStatus(OrderStatus.ASSIGNED);
            order.setCourierId(courier.getId());
            courier.setBusy(true);
        }
    }

    private void assignCourier(Order order) {
        if (
            order.getStatus() == OrderStatus.ASSIGNED ||
            order.getStatus() == OrderStatus.SELF_PICKUP
        ) {
            ConsoleIO.outText(
                "Are you sure you want to change the delivery type?\n1 - YES\n2 - NO"
            );
            while (true) {
                boolean f = false;
                int i = ConsoleIO.readInt("Enter: ");
                switch (i) {
                    case 1:
                        releaseCourier(order);
                        order.setStatus(OrderStatus.CREATED);
                        f = true;
                        break;
                    case 2:
                        return;
                    default:
                        ConsoleIO.outText(
                            "Invalid value; enter a number from 1 to 2."
                        );
                }
                if (f) {
                    break;
                }
            }
        }
        if (order.getStatus() == OrderStatus.CREATED) {
            ConsoleIO.outText(
                "Select a delivery method (enter the number):\n1)Ordinary\n2)Express\n3)Self-pickup"
            );
            while (true) {
                int i = ConsoleIO.readInt("Enter: ");
                switch (i) {
                    case 1:
                        tryAssignCourier(order, TypeDelivery.ORDINARY);
                        return;
                    case 2:
                        tryAssignCourier(order, TypeDelivery.EXPRESS);
                        return;
                    case 3:
                        ConsoleIO.outText(
                            "Your order will be prepared within half an hour; please visit the selected pickup point to collect it."
                        );
                        order.setStatus(OrderStatus.SELF_PICKUP);
                        return;
                    default:
                        ConsoleIO.outText(
                            "Invalid value; enter a number from 1 to 3."
                        );
                }
            }
        } else {
            ConsoleIO.outText(
                "The item has either been handed over for delivery or received."
            );
        }
    }

    public static void orderProcessing() {}
}
