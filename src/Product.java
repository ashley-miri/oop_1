public class Product {

    //data fields
    private final String name;
    private final int cost;
    private final int weight;

    public Product(String name, int coast, int weight) {
        this.name = name;
        this.cost = coast;
        this.weight = weight;
    }

    //getters
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
