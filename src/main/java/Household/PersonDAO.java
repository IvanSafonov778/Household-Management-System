package Household;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDAO extends DatabaseServiceDAO {





    public  void updatePerson(int person_id, String name, String lastname, int houseHoldId) {
        String updatePersonQuery = "UPDATE PERSON SET name=?, lastname=?, household_id=? WHERE person_id=?;";


        if (!doesIdExist(houseHoldId, "HOUSEHOLD")) {
            System.out.println("Error: Household with id " + houseHoldId + " does not exist in the database.");
            return;
        }

        try (PreparedStatement statement = MyConnection.getInstance().prepareStatement(updatePersonQuery)) {
            statement.setString(1, name);
            statement.setString(2, lastname);
            statement.setInt(3, houseHoldId);
            statement.setInt(4, person_id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Person with id " + person_id + " has been updated!");
            } else {
                System.out.println("Error: Person with id " + person_id + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating person with id " + person_id, e);
        }
    }


    public  void deletePerson(int person_id) {
        String checkQuery = "SELECT COUNT(*) FROM PET WHERE owner_id=?;";


        try (PreparedStatement statement = MyConnection.getInstance().prepareStatement(checkQuery)) {
            statement.setInt(1, person_id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            if (resultSet.getInt(1) > 0) {
                System.out.println("First you have to delete pets which belong to current person");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while checking if id exists", e);
        }

        String deleteByIdQuery = "DELETE FROM PERSON WHERE person_id=?;";

        try (PreparedStatement deleteById = MyConnection.getInstance().prepareStatement(deleteByIdQuery)) {

            deleteById.setInt(1, person_id);

            int rowsAffected = deleteById.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Person with id " + person_id + " was successfully deleted from the list");
            } else {
                System.out.println("Error: Person with id " + person_id + " not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting a person", e);
        }

    }
    public  void readPerson(int person_id) {
        String selectPersonQuery = "SELECT * FROM PERSON WHERE person_id=?;";

        try (PreparedStatement findStatement = MyConnection.getInstance().prepareStatement(selectPersonQuery)) {
            findStatement.setInt(1, person_id);

            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    String name = resultSet.getString("name");
                    String lastname = resultSet.getString("lastname");
                    int householdId = resultSet.getInt("household_id");
                    int personId = resultSet.getInt("person_id");

                    System.out.println("Person id " + personId + " " + name + " " + lastname + " belongs to the household " + householdId);
                    System.out.println(" ");
                } while (resultSet.next());
            } else {
                System.out.println("No person found with ID " + person_id);
                System.out.println(" ");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading person", e);
        }
    }

    public  void createPerson(Person person, int haushalt_id) {

        if (!doesIdExist(haushalt_id, "HOUSEHOLD")) {
            System.out.println("Error: Household with id " + haushalt_id + " does not exist in the database.");
            return;
        }

        String insertPersonQuery = "INSERT INTO PERSON (name,lastname,household_id) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = MyConnection.getInstance().prepareStatement(insertPersonQuery, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastname());
            preparedStatement.setInt(3, haushalt_id);

            int RowsAffected = preparedStatement.executeUpdate();

            if (RowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int personId = generatedKeys.getInt(1);
                    person.setPersonId(personId);
                    System.out.println("Owner " + person.getName() + " " + person.getLastname() + " with id " + personId + " has been created!");
                }

            } else {
                System.out.println("Something went wrong");
            }


        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting a new owner", e);
        }

    }
    public int gettotal() {
        return super.gettotal("PERSON");
    }

}
