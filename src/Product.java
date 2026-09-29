public class Product {

    //data fields
    private final int id;
    private final String name;
    private final int cost;
    private final int weight;

    public Product(int id, String name, int coast, int weight) {
        this.id = id;
        this.name = name;
        this.cost = coast;
        this.weight = weight;
    }

    //getters
    public int get_id() {
        return id;
    }

    public String get_name() {
        return name;
    }

    public int get_cost() {
        return cost;
    }

    public int get_weight() {
        return weight;
    }
}
