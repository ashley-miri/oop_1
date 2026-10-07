import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataBase {

    //Connection to db
    private static final String URL = "jdbc:sqlite:data/delivery.db";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    //initialising db
    public void init() throws SQLException {
        String products = """
        CREATE TABLE IF NOT EXISTS products (
            id     INTEGER PRIMARY KEY,
            name   TEXT    NOT NULL,
            name_lower TEXT    NOT NULL,
            cost   INTEGER NOT NULL,
            weight INTEGER NOT NULL
        )""";
        String couriers = """
        CREATE TABLE IF NOT EXISTS couriers (
            id     TEXT PRIMARY KEY,
            name   TEXT    NOT NULL,
            phone   TEXT    NOT NULL,
            type_delivery   TEXT    NOT NULL
        )""";
        String clients = """
        CREATE TABLE IF NOT EXISTS clients (
            id     TEXT PRIMARY KEY,
            name   TEXT    NOT NULL,
            phone   TEXT    NOT NULL,
            address   TEXT    NOT NULL
        )""";
        String admins = """
        CREATE TABLE IF NOT EXISTS admins (
            id     TEXT PRIMARY KEY,
            name   TEXT    NOT NULL,
            phone   TEXT    NOT NULL
        )""";
        try (
            Connection conn = connect();
            Statement st = conn.createStatement()
        ) {
            st.execute(products);
            st.execute(couriers);
            st.execute(clients);
            st.execute(admins);
        }
    }

    //add new client
    public void saveClient(Client client) throws SQLException {
        String sql =
            "INSERT INTO clients (id, name, phone, address) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, client.getId().toString());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getDeliveryAddress());
            ps.executeUpdate();
        }
    }

    public Client findClient(UUID id) throws SQLException {
        String sql =
            "SELECT id, name, phone, address FROM clients WHERE id = ?";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address")
                    );
                }
            }
        }
        return null;
    }

    public void saveProduct(Product product) throws SQLException {
        String sql =
            "INSERT INTO products (name, name_lower, cost, weight) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getName().toLowerCase());
            ps.setInt(3, product.getCost());
            ps.setInt(4, product.getWeight());
            ps.executeUpdate();
        }
    }

    public List<Product> findProduct(String name) throws SQLException {
        List<Product> result = new ArrayList<>();
        String sql =
            "SELECT id, name, name_lower, cost, weight FROM products WHERE name_lower LIKE ?";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + name.trim().toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(
                        new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("cost"),
                            rs.getInt("weight")
                        )
                    );
                }
            }
        }
        return result;
    }

    //выглядит дико но за счет %% помещает вообще все продукты в список что удобно
    public List<Product> loadProducts() throws SQLException {
        return findProduct("");
    }

    public void saveCourier(Courier courier) throws SQLException {
        String sql =
            "INSERT INTO couriers (id, name, phone, type_delivery) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, courier.getId().toString());
            ps.setString(2, courier.getName());
            ps.setString(3, courier.getPhone());
            ps.setString(4, courier.getType());
            ps.executeUpdate();
        }
    }

    public List<Courier> loadCouriers() throws SQLException {
        List<Courier> result = new ArrayList<>();
        String sql = "SELECT id, name, phone, type_delivery FROM couriers";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                result.add(
                    new Courier(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("type_delivery")
                    )
                );
            }
        }
        return result;
    }
}
