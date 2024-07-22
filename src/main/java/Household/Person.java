package Household;

public class Person {


    private int personId;

    private String name;
    private String lastname;
    private int household_Id;


    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Person(String name, String lastname, int household_Id) {
        this.name = name;
        this.lastname = lastname;
        this.household_Id = household_Id;
    }

    public Person(int personId, String name, String lastname, int householdId) {
        this.personId = personId;
        this.name = name;
        this.lastname = lastname;
        this.household_Id = householdId;
    }
}
