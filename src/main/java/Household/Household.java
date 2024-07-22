package Household;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Household {


    private int householdId;

    private String name;
    private String location;


    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(int householdId) {
        this.householdId = householdId;
    }



    public Household(int householdId, String name, String location) {
        this.householdId = householdId;
        this.name = name;
        this.location = location;
    }

    public Household( String name, String location) {

        this.name = name;
        this.location = location;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("id: ").append(getHouseholdId()).append("\n").append("Name: ").append(getName()).append("\n").append("Location: ").append(getLocation()).append("\n");
        return sb.toString();
    }

    ;


}
