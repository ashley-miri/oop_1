public class Courier extends Person {

    //datafields
    private final String typeDelivery;
    private int orderId = -1;

    //constructor
    public Courier(int id, String name, String phone, String typeDelivery) {
        super(id, name, phone);
        this.typeDelivery = typeDelivery;
    }

    //getters
    public String getType() {
        return typeDelivery;
    }

    public int getOrderId() {
        return orderId;
    }

    //setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
