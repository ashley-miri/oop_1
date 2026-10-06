import java.util.ArrayList;
import java.util.List;

public class Order {

    //data fields
    private final int id;
    private final int clientId;
    private final List<Product> items = new ArrayList<>();
    private int courierId;
    private String time;

    //constructor
    public Order(int id, int clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    //getters
    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getCourierId() {
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
    public void setCourierId(int courierId) {
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

    //remuve
    public void removeItem(Product item) {
        this.items.remove(item);
    }
}
