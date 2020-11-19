package Application.Database.DTO;

public class ClubCreateDTO {
    private final String name;

    public ClubCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
