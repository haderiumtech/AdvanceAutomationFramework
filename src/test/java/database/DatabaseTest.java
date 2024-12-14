package database;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {

    private DatabaseUtils databaseUtils;

    @BeforeClass
    public void setUp() {
        // Get the singleton instance of DatabaseUtils
        databaseUtils = DatabaseUtils.getInstance();
    }
    
    @Test
    public void testDatabaseConnection() {
        DatabaseUtils dbUtils = DatabaseUtils.getInstance();
        Connection connection = dbUtils.getConnection();

        if (connection != null) {
            System.out.println("Database connection established successfully!");
        } else {
            System.out.println("Failed to establish database connection.");
        }
    }

    @Test (priority = 3)
    public void testSelectQuery() {
        String query = "SELECT * FROM users WHERE username = ?";
        try (ResultSet resultSet = databaseUtils.executeQuery(query, "james")) {
            Assert.assertTrue(resultSet.next(), "User not found in the database");
            String username = resultSet.getString("username");
            Assert.assertEquals(username, "james", "Username does not match");
        } catch (SQLException e) {
            Assert.fail("SQL Exception: " + e.getMessage());
        }
    }

//    @Test
//    public void testUpdateQuery() {
//        String updateQuery = "UPDATE users SET last_login = NOW() WHERE username = ?";
//        int rowsUpdated = databaseUtils.executeUpdate(updateQuery, "test_user");
//        Assert.assertTrue(rowsUpdated > 0, "No rows were updated");
//    }

    @AfterClass
    public void tearDown() {
        // Close the database connection after all tests
        databaseUtils.closeConnection();
    }
}
