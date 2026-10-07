import java.util.UUID;

public class Courier extends Person {

    //datafields
    private final String typeDelivery;
    private boolean busyStatus = false;

    //constructor
    public Courier(UUID id, String name, String phone, String typeDelivery) {
        super(id, name, phone);
        this.typeDelivery = typeDelivery;
    }

    //getters
    public String getType() {
        return typeDelivery;
    }

    public boolean isBusy() {
        return busyStatus;
    }

    //setters
    public void setBusy(boolean busyStatus) {
        this.busyStatus = busyStatus;
    }
}
