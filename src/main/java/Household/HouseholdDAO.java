package Household;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HouseholdDAO extends DatabaseServiceDAO{






    public void updateHousehold(int household_id, String name, String location) {
        String updateHouseholdQuery = "UPDATE HOUSEHOLD SET name=?, location=? WHERE id=?;";

        try (PreparedStatement statement = MyConnection.getInstance().prepareStatement(updateHouseholdQuery)) {
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setInt(3, household_id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Household with id " + household_id + " has been updated!");
            } else {
                System.out.println("Error: Household with id " + household_id + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating household with id " + household_id, e);
        }
    }


    public  void deleteHousehold(int household_id) {
        String checkQuery = "SELECT COUNT(*) FROM PERSON WHERE household_id=?;";

        try (PreparedStatement statement = MyConnection.getInstance().prepareStatement(checkQuery)) {
            statement.setInt(1, household_id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            if (resultSet.getInt(1) > 0) {
                System.out.println("First you have to delete persons which belong to current person");
                return;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if id exists", e);
        }

        String deleteByIdQuery = "DELETE FROM HOUSEHOLD WHERE id=?;";


        try (PreparedStatement deleteById = MyConnection.getInstance().prepareStatement(deleteByIdQuery)) {

            deleteById.setInt(1, household_id);

            int rowsAffected = deleteById.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Household with id " + household_id + " was successfully deleted from the list");
            } else {
                System.out.println("Error: Household with id " + household_id + " not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting a household", e);
        }
    }


    public  void readHousehold(int haushalt_id) {
        String selectHouseholdQuery = "SELECT * FROM HOUSEHOLD WHERE ID=?;";

        try (PreparedStatement findStatement = MyConnection.getInstance().prepareStatement(selectHouseholdQuery)) {
            findStatement.setInt(1, haushalt_id);

            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");

                    System.out.println("Household id: " + haushalt_id + "; \nName: " + name + "; \nLocation: " + location);
                    System.out.println(" ");
                } while (resultSet.next());
            } else {
                System.out.println("No household found with ID " + haushalt_id);
                System.out.println(" ");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading household", e);
        }
    }


    public  void createHousehold(Household household) {


        String insertHouseholdQuery = "INSERT INTO HOUSEHOLD (name,location) VALUES (?, ?)";

        try (PreparedStatement preparedStatement =  MyConnection.getInstance().prepareStatement(insertHouseholdQuery, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setInt(1, household.getHouseholdId());
            preparedStatement.setString(1, household.getName());
            preparedStatement.setString(2, household.getLocation());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int householdId = generatedKeys.getInt(1);
                    household.setHouseholdId(householdId);

                    System.out.println("Household " + household.getName() + " with id " + householdId + " has been created!");
                }
            } else {
                System.out.println("Something went wrong");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting a new household", e);
        }
    }

    public int gettotal() {
        return super.gettotal("HOUSEHOLD");
    }

}
