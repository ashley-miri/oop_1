import java.util.UUID;

public abstract class Person {

    //data fields
    private final UUID id;
    private final String name;
    private final String phone;

    //constructor
    protected Person(UUID id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    //getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
