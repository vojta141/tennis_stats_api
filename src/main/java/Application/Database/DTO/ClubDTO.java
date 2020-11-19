package Application.Database.DTO;

public class ClubDTO {
    private final int id;
    private final String name;

    public ClubDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
