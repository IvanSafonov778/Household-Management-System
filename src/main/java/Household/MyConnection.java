package Household;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private static Connection instance;
    public static Connection getInstance() {
    if (instance == null) {
        instance = createConnection();
    }
        return instance;

    }

    private static Connection createConnection() {
        try (InputStream configFileInput = DatabaseServiceDAO.class.getClassLoader().getResourceAsStream("connection.properties")) {
            Properties properties = new Properties();
            properties.load(configFileInput);

            try {
                return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
            } catch (SQLException e) {
                throw new RuntimeException("Failed to establish a database connection", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load connection properties", e);
        }
    }
}
