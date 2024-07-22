package Household;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public  class DatabaseServiceDAO {

    private static DatabaseServiceDAO instance;
    private Connection connection;


    public DatabaseServiceDAO() {
        this.connection = createConnection();
    }

    public static DatabaseServiceDAO getInstance() {
        if (instance == null) {
            instance = new DatabaseServiceDAO();
        }
        return instance;
    }

    private Connection createConnection() {
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

    protected int gettotal(String table) {
        String checkQuery = "";

            checkQuery = "SELECT COUNT(*) FROM " + table + ";";


        try (PreparedStatement statement = connection.prepareStatement(checkQuery)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting count of instances in the Table", e);
        }
    }

    boolean doesIdExist(int id, String table) {
        String checkQuery = "";

        if (Objects.equals(table, "HOUSEHOLD")) {
            checkQuery = "SELECT COUNT(*) FROM " + table + " WHERE id =?;";
        } else {
            checkQuery = "SELECT COUNT(*) FROM " + table + " WHERE person_id =?;";
        }

        try (PreparedStatement statement = connection.prepareStatement(checkQuery)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if id exists", e);
        }
    }

}
