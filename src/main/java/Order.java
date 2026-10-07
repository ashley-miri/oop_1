import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    //data fields
    private final int id;
    private final UUID clientId;
    private final List<Product> items = new ArrayList<>();
    private UUID courierId;
    private String time;

    //constructor
    public Order(int id, UUID clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    //getters
    public int getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getCourierId() {
        return courierId;
    }

    public String getTime() {
        return time;
    }

    public List<Product> getItems() {
        return items;
    }

    public int getCost() {
        int cost = 0;
        for (Product item : items) {
            cost += item.getCost();
        }
        return cost;
    }

    //setters
    public void setCourierId(UUID courierId) {
        this.courierId = courierId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void addItem(Product item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    //remove
    public void removeItem(Product item) {
        this.items.remove(item);
    }
}
