import java.util.UUID;

public class Client extends AuthUser {

    //datafields
    private String deliveryAddress;

    //constructor
    public Client(
        UUID id,
        String name,
        String phone,
        String deliveryAddress,
        String login,
        String passwordHash
    ) {
        super(id, name, phone, login, passwordHash); // Вызов конструктора Person
        this.deliveryAddress = deliveryAddress;
    }

    //getters
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
