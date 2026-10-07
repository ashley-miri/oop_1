import java.util.UUID;

public abstract class AuthUser extends Person {

    private final String login;
    private String passwordHash;

    protected AuthUser(
        UUID id,
        String name,
        String phone,
        String login,
        String passwordHash
    ) {
        super(id, name, phone);
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
