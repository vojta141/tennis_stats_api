package Application.Database.DTO;

public class ClubCreateDTO {
    private String name;

    public ClubCreateDTO() {
    }

    public ClubCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
