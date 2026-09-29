public class Courier {

    //datafields
    private final int id;
    private final String type_delivery;
    private int order_id;

    //constructor
    public Courier(int id, String type_delivery) {
        this.id = id;
        this.type_delivery = type_delivery;
    }

    //getters
    public int get_id() {
        return id;
    }

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
