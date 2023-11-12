/*
 * Library Management System
 * Name: Fernanda Frederico Ribeiro da Silva
 * Class: Software Development I CEN-3024C-16046
 * Professor: Walauskis
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnector class establishes a connection to a MySQL database.
 * This class contains a method to connect to the database using the JDBC driver.
 * The database URL, username, and password are specified as class variables.
 *
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