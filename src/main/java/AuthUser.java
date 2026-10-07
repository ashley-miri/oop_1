import java.util.UUID;

public abstract class AuthUser extends Person {

    protected AuthUser(UUID id, String name, String phone) {
        super(id, name, phone);
    }
}
