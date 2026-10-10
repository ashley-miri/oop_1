import java.util.UUID;

public class Courier extends Person {

    //datafields
    private final TypeDelivery typeDelivery;
    private boolean busyStatus = false;

    //constructor
    public Courier(
        UUID id,
        String name,
        String phone,
        TypeDelivery typeDelivery
    ) {
        super(id, name, phone);
        this.typeDelivery = typeDelivery;
    }

    //getters
    public TypeDelivery getType() {
        return typeDelivery;
    }

    public boolean isBusy() {
        return busyStatus;
    }

    //setters
    public void setBusy(boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public String toString() {
        return (
            getName() +
            ", тел. " +
            getPhone() +
            ", доставка: " +
            typeDelivery.getLabel() +
            (busyStatus ? " — занят" : " — свободен")
        );
    }
}
