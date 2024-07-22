package Household;

public class Pet {



    private int pet_Id;
    private String name;
    private String type;
    private int owner_id;


    public Pet(String name, String type, int ownerId) {
        this.name = name;
        this.type = type;
        this.owner_id = ownerId;
    }

    public Pet(int petId, String name, String type, int ownerId) {
        this.pet_Id = petId;
        this.name = name;
        this.type = type;
        this.owner_id = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPetId() {
        return pet_Id;
    }

    public void setPetId(int petId) {
        this.pet_Id = petId;
    }


}
