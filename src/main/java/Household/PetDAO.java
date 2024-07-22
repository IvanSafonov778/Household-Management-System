package Household;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class  PetDAO extends DatabaseServiceDAO {

    public void updatePet(int pet_id, String name, String type, int person_id) {

        String updatePetQuery = "UPDATE PET SET name=?, type=?, owner_id=? WHERE pet_id=?;";

        if (!doesIdExist(person_id, "PERSON")) {
            System.out.println("Error: Owner with id " + person_id + " does not exist in the database.");
            return;
        }

        try (PreparedStatement statement = MyConnection.getInstance().prepareStatement(updatePetQuery)) {

            statement.setString(1, name);
            statement.setString(2, type);
            statement.setInt(3, person_id);
            statement.setInt(4, pet_id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pet with id " + pet_id + " has been updated!");
            } else {
                System.out.println("Error: Pet with id " + pet_id + " not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating person with id " + pet_id, e);
        }
    }


    public  void deletePet(int pet_id) {
        String deleteByIdQuery = "DELETE FROM PET WHERE pet_id=?;";

        try (PreparedStatement deleteById = MyConnection.getInstance().prepareStatement(deleteByIdQuery)) {

            deleteById.setInt(1, pet_id);

            int rowsAffected = deleteById.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pet with id " + pet_id + " was successfully deleted from the list");
            } else {
                System.out.println("Error: Pet with id " + pet_id + " not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting a pet", e);
        }

    }



    public  void readPet(int pet_id) {

        String selectPetQuery = "SELECT * FROM pet WHERE pet_id=?;";

        try (PreparedStatement findStatement = MyConnection.getInstance().prepareStatement(selectPetQuery)) {
            findStatement.setInt(1, pet_id);

            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next()) {
                do {


                    int petid = resultSet.getInt("pet_id");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    int ownerid = resultSet.getInt("owner_id");

                    System.out.println("Pet id " + petid + ", " + name + " is a " + type + " belongs to the person " + ownerid);
                    System.out.println(" ");
                } while (resultSet.next());
            } else {
                System.out.println("No pet found with ID " + pet_id);
                System.out.println(" ");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading pet", e);
        }
    }
        public void createPet(Pet pet, int person_id) {

            if (!doesIdExist(person_id, "PERSON")) {
                System.out.println("Error: Person with id " + person_id + " does not exist in the database.");
                return;
            }

            String insertPetQuery = "INSERT INTO PET (name,type,owner_id) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = MyConnection.getInstance().prepareStatement(insertPetQuery, Statement.RETURN_GENERATED_KEYS)) {
//                preparedStatement.setInt(1, pet.getPetId());
                preparedStatement.setString(1, pet.getName());
                preparedStatement.setString(2, pet.getType());
                preparedStatement.setInt(3, person_id);

                int RowsAffected = preparedStatement.executeUpdate();

                if (RowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        int petId = generatedKeys.getInt(1);
                        pet.setPetId(petId);

                        System.out.println("Pet " + pet.getType() + " " + pet.getName() + " with id " + petId + " has been created! Owner id is " + person_id);
                    }


                } else {
                    System.out.println("Something went wrong");
                }


            } catch (SQLException e) {
                throw new RuntimeException("Error while inserting a new pet", e);
            }

        }

        public int gettotal() {
            return super.gettotal("PET");
        }


    }
