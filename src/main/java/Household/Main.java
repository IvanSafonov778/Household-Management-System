package Household;

import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static PetDAO petDAO;
    private static HouseholdDAO householdDAO;
    private static PersonDAO personDAO;

    public static void main(String[] args) {
        String choice="";
        Scanner sc = new Scanner(System.in);
        petDAO = new PetDAO();
        householdDAO = new HouseholdDAO();
        personDAO = new PersonDAO();

        while (!Objects.equals(choice, "0")) {
            printMenu();
            choice = sc.nextLine();


            switch (choice) {
                case "1" -> create();
                case "2" -> read();
                case "3" -> update();
                case "4" -> delete();
                default -> {
                }
            }

        }
        System.out.println("THE END, BYE!");

    }

    static void printMenu() {
        System.out.println("CHOOSE YOUR OPTION:");
        System.out.println("1. Create");
        System.out.println("2. Read");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("0 to exit");
    }

    static boolean isInt(String input) throws NumberFormatException {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    static String selectOption() {
        Scanner sc = new Scanner(System.in);
        String selectedOption = "";

        while (!Objects.equals(selectedOption, "0") && !Objects.equals(selectedOption, "1") && !Objects.equals(selectedOption, "2") && !Objects.equals(selectedOption, "3")) {
            System.out.println("1. Household");
            System.out.println("2. Person");
            System.out.println("3. Pet");
            System.out.println("Enter 0 to exit");

            selectedOption = sc.nextLine();
        }

        return selectedOption;
    }

    static void create() {

        Scanner sc = new Scanner(System.in);

        String type = selectOption();

        if (Objects.equals(type, "1")) {

//            int totalHouse=1+ householdDAO.gettotal();

            System.out.println("Create new household: ");
            System.out.println("Enter household's name ");
            String name = sc.nextLine();
            System.out.println("Enter household's location ");
            String location = sc.nextLine();

            Household newHousehold = new Household(name, location);
            householdDAO.createHousehold(newHousehold);

        } else if (Objects.equals(type, "2")) {

//            int totalPerson=1+ personDAO.gettotal();

            System.out.println("Create new person: ");
            System.out.println("Enter person's name ");
            String name = sc.nextLine();
            System.out.println("Enter person's lastname ");
            String lastname = sc.nextLine();
            System.out.println("Enter the ID of the household the person belongs to ");

            int householdId = sc.nextInt();
            sc.nextLine();
            Person newPerson = new Person(name, lastname, householdId);

            personDAO.createPerson(newPerson, householdId);
        } else if (Objects.equals(type, "3")) {

//            int totalPet=1+ petDAO.gettotal();

            System.out.println("Create new pet: ");
            System.out.println("Enter pet's name ");
            String name = sc.nextLine();
            System.out.println("Enter pet's type ");
            String petType = sc.nextLine();
            System.out.println("Enter the pet owner's ID");
            int personId = sc.nextInt();
            sc.nextLine();

            Pet newPet = new Pet(name, petType, personId);
            petDAO.createPet(newPet, personId);
        }
    }

    static void read() {
        Scanner sc = new Scanner(System.in);

        String type = selectOption();

        if (Objects.equals(type, "1")) {
            System.out.println("Read household's data ");
            System.out.println("Enter household id: ");
            String householdId = sc.nextLine();
            if (isInt(householdId)) {
                householdDAO.readHousehold(Integer.parseInt(householdId));
            } else {
                System.out.println("DOES NOT EXIST");
            }
        } else if (Objects.equals(type, "2")) {
            System.out.println("Read person's data ");
            System.out.println("Enter person id: ");
            String personId = sc.nextLine();

            if (isInt(personId)) {
                personDAO.readPerson(Integer.parseInt(personId));
            } else {
                System.out.println("DOES NOT EXIST");
            }
        } else if (Objects.equals(type, "3")) {
            System.out.println("Read pet's data ");
            System.out.println("Enter pet id: ");
            String petId = sc.nextLine();

            if (isInt(petId)) {
                petDAO.readPet(Integer.parseInt(petId));
            } else {
                System.out.println("DOES NOT EXIST");
            }
        }
    }

    static void update() {
        Scanner sc = new Scanner(System.in);

        String type = selectOption();

        if (Objects.equals(type, "1")) {
            System.out.println("Update household's data: ");
            System.out.println("Enter household id: ");
            int householdId = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter new household's name: ");
            String newName = sc.nextLine();

            System.out.println("Enter new household's location: ");
            String newLocation = sc.nextLine();

            householdDAO.updateHousehold(householdId, newName, newLocation);

        } else if (Objects.equals(type, "2")) {
            System.out.println("Update person's data: ");
            System.out.println("Enter person id: ");
            int personId = sc.nextInt();
            sc.nextLine();

            System.out.println("Enter new person's name: ");
            String newName = sc.nextLine();

            System.out.println("Enter new person's lastname: ");
            String newLastname = sc.nextLine();

            System.out.println("Enter the ID of the household the person belongs to: ");
            int householdId = sc.nextInt();
            sc.nextLine();

            personDAO.updatePerson(personId, newName, newLastname, householdId);

        } else if (Objects.equals(type, "3")) {
            System.out.println("Update pet's data: ");
            System.out.println("Enter pet id: ");
            int petId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter new pet's name: ");
            String newName = sc.nextLine();
            System.out.println("Enter pet's type: ");
            String newType = sc.nextLine();
            System.out.println("Enter the ID of the pet's owner: ");
            int ownerId = sc.nextInt();
            sc.nextLine();

            petDAO.updatePet(petId, newName, newType, ownerId);

        }
    }

    static void delete() {
        Scanner sc = new Scanner(System.in);

        String type = selectOption();

        if (Objects.equals(type, "1")) {
            System.out.println("Delete household ");
            System.out.println("Enter household id: ");
            String householdId = sc.nextLine();

            if (isInt(householdId)) {
                householdDAO.deleteHousehold(Integer.parseInt(householdId));
            } else {
                System.out.println("Invalid household id!");
            }

        } else if (Objects.equals(type, "2")) {
            System.out.println("Delete person ");
            System.out.println("Enter person id: ");
            String personId = sc.nextLine();

            if (isInt(personId)) {
                personDAO.deletePerson(Integer.parseInt(personId));
            } else {
                System.out.println("Invalid person id!");
            }

        } else if (Objects.equals(type, "3")) {
            System.out.println("Delete pet ");
            System.out.println("Enter pet id: ");
            String petId = sc.nextLine();

            if (isInt(petId)) {
                petDAO.deletePet(Integer.parseInt(petId));
            } else {
                System.out.println("Invalid pet id!");
            }
        }
    }



}
