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

    public int get_id() {
        return id;
    }

    public String get_address() {
        return delivery_address;
    }

    public String get_client() {
        return client;
    }

    public String get_type() {
        return type_delivery;
    }

    public String get_courier() {
        return courier;
    }

    public String get_time() {
        return time;
    }

    public int get_coast() {
        return coast;
    }

    public void set_type(String type_delivery) {
        this.type_delivery = type_delivery;
    }

    public void set_courier(String courier) {
        this.courier = courier;
    }

    public void set_tyime(String time) {
        this.time = time;
    }

    public void set_type(int coast) {
        this.coast = coast;
    }
}
