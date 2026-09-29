public abstract class Person {

    //data fields
    protected int id;
    protected String name;
    protected String phone;

    //constructor
    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    //getters
    public int get_id() {
        return id;
    }

    public String get_name() {
        return name;
    }

    public String get_phone() {
        return phone;
    }
}
