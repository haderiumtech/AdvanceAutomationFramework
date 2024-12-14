package utils;

import config.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    private static DatabaseUtils instance;
    private Connection connection;

    // Updated to use Configuration class
    private String url;
    private String username;
    private String password;
    private String driver;

    // Private constructor to enforce Singleton pattern
    private DatabaseUtils() {
        try {
            // Retrieve database connection details from Configuration class
            this.url = Configuration.get("db.url");
            this.username = Configuration.get("db.username");
            this.password = Configuration.get("db.password");
            this.driver = Configuration.get("db.driver");

            // Load the MySQL driver
            Class.forName(driver);

            // Establish the connection
            this.connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }

    // Public method to get the singleton instance
    public static DatabaseUtils getInstance() {
        if (instance == null) {
            synchronized (DatabaseUtils.class) {
                if (instance == null) {
                    instance = new DatabaseUtils();
                }
            }
        }
        return instance;
    }

    // Get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Execute a SELECT query
    public ResultSet executeQuery(String query, Object... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query: " + e.getMessage(), e);
        }
    }

    // Execute an INSERT, UPDATE, or DELETE query
    public int executeUpdate(String query, Object... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update: " + e.getMessage(), e);
        }
    }

    // Set parameters for the PreparedStatement
    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

    // Close resources
    public void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Failed to close resource: " + e.getMessage());
                }
            }
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
