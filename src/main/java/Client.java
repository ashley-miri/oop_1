public class Client extends Person {

    //datafields
    private String delivery_address;

    //constructor
    public Client(int id, String name, String phone, String delivery_address) {
        super(id, name, phone); // Вызов конструктора Person
        this.delivery_address = delivery_address;
    }

    //getters
    public String getDelivery_address() {
        return delivery_address;
    }
}
