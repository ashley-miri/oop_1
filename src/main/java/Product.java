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
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }
}
