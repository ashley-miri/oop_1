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

    private void setOrderCourier(Order order, Courier courier) {
        if (courier.isBusy()) {
            throw new BusyCourierException(
                "Courier id " + courier.getId() + " is busy now"
            );
        }
        order.setCourierId(courier.getId());
        order.setStatus(OrderStatus.ASSIGNED);
        courier.setBusy(true);
    }

    private void completeOrder(Order order, Courier courier) {
        if (!courier.getId().equals(order.getCourierId())) {
            throw new BusyCourierException(
                "This courier is not involved with this order"
            );
        }
        order.setStatus(OrderStatus.DELIVERED);
        courier.setBusy(false);
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
            System.out.print(
                "\nAll couriers for this delivery method are currently busy. Please try again later or change the delivery method."
            );
        } else {
            try {
                setOrderCourier(order, courier);
            } catch (BusyCourierException e) {
                System.out.print(e);
            }
        }
    }

    private void assignCourier(Order order) {
        if (order.getStatus() == OrderStatus.CREATED) {
            System.out.print(
                "\nSelect a delivery method (enter the number):\n1)Ordinary\nExpress\nSelf-pickup"
            );
            while (true) {
                int i = ConsoleIO.readInt("\nEnter: ");
                switch (i) {
                    case 1:
                        tryAssignCourier(order, TypeDelivery.ORDINARY);
                        return;
                    case 2:
                        tryAssignCourier(order, TypeDelivery.EXPRESS);
                        return;
                    case 3:
                        System.out.print(
                            "\nYour order will be prepared within half an hour; please visit the selected pickup point to collect it."
                        );
                        return;
                    default:
                        System.out.print(
                            "\nInvalid value; enter a number from 1 to 3."
                        );
                }
            }
        }
    }

    public static void orderProcessing() {}
}
