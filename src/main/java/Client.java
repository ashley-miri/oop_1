public class Client extends Person {

    //datafields
    private String deliveryAddress;

    //constructor
    public Client(int id, String name, String phone, String deliveryAddress) {
        super(id, name, phone); // Вызов конструктора Person
        this.deliveryAddress = deliveryAddress;
    }

    //getters
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
