public class Order {

    private final int id;
    private final String delivery_address;
    private final String client;
    private String type_delivery;
    private String courier;
    private String time;
    private int coast;

    public Order(int id, String delivery_address, String client) {
        this.id = id;
        this.client = client;
        this.delivery_address = delivery_address;
    }

    public get_id() {
        return id;
    }

    public get_addr() {
        return delivery_address;
    }
}
