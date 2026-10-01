import java.util.ArrayList;
import java.util.List;

public class Order {

    //data fields
    private final int id;
    private final String deliveryAddress;
    private final String client;
    private final List<String> items = new ArrayList<>();
    private String typeDelivery;
    private String courier;
    private String time;
    private int coast;

    //constructor
    public Order(int id, String deliveryAddress, String client) {
        this.id = id;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getAddress() {
        return deliveryAddress;
    }

    public String getClient() {
        return client;
    }

    public String getType() {
        return typeDelivery;
    }

    public String getCourier() {
        return courier;
    }

    public String getTime() {
        return time;
    }

    public int getCoast() {
        return coast;
    }

    public List<String> getItems() {
        return items;
    }

    //setters
    public void setType(String typeDelivery) {
        this.typeDelivery = typeDelivery;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public void setItem(String item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    //remuve
    public void removeItem(String item) {
        this.items.remove(item);
    }
}
