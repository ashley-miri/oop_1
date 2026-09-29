public class Courier extends Person {

    //datafields
    private final String type_delivery;
    private int order_id = -1;

    //constructor
    public Courier(int id, String name, String phone, String type_delivery) {
        super(id, name, phone);
        this.type_delivery = type_delivery;
    }

    //getters
    public String get_type() {
        return type_delivery;
    }

    public int get_order_id() {
        return order_id;
    }

    //setters
    public void set_order_id(int order_id) {
        this.order_id = order_id;
    }
}
