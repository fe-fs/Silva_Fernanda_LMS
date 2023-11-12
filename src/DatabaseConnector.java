import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnector class establishes a connection to a MySQL database.
 * <p>
 * This class contains a method to connect to the database using the JDBC driver.
 * The database URL, username, and password are specified as class variables.
 * <p>
 */
public class DatabaseConnector {

    private String url = "jdbc:mysql://localhost:3306/librarydata";
    private String username = "root";
    private String password = "Sql#1";

    public Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}