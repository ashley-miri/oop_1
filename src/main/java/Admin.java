import java.util.UUID;

public class Admin extends AuthUser {

    public Admin(
        UUID id,
        String name,
        String phone,
        String login,
        String passwordHash
    ) {
        super(id, name, phone, login, passwordHash);
    }
}
