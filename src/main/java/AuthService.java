import java.sql.SQLException;

public class AuthService {

    private final DataBase data;

    public AuthService(DataBase data) {
        this.data = data;
    }

    public AuthUser tryLogin(String login, String password)
        throws SQLException {
        AuthUser user = data.findUserByLogin(login);
        if (user == null || !user.checkPassword(password)) {
            return null;
        }
        return user;
    }
}
