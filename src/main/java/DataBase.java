import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    //function
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
            id     INTEGER PRIMARY KEY,
            name   TEXT    NOT NULL,
            phone   TEXT    NOT NULL,
            type_delivery   TEXT    NOT NULL
        )""";
        String clients = """
        CREATE TABLE IF NOT EXISTS clients (
            id     INTEGER PRIMARY KEY,
            name   TEXT    NOT NULL,
            phone   TEXT    NOT NULL,
            address   TEXT    NOT NULL
        )""";
        try (
            Connection conn = connect();
            Statement st = conn.createStatement()
        ) {
            st.execute(products);
            st.execute(couriers);
            st.execute(clients);
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
            ps.setInt(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPhone());
            ps.setString(4, client.getDeliveryAddress());
            ps.executeUpdate();
        }
    }

    public Client findClient(int id) throws SQLException {
        String sql =
            "SELECT id, name, phone, address FROM clients WHERE id = ?";
        try (
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address")
                    );
                }
            }
        }
        return null;
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
                        rs.getInt("id"),
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
