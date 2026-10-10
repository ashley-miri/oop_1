import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
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

    public static String hashPassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(
                rawPassword.getBytes(StandardCharsets.UTF_8)
            );
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 недоступен", e);
        }
    }

    public boolean checkPassword(String rawPassword) {
        return passwordHash.equals(hashPassword(rawPassword));
    }
}
