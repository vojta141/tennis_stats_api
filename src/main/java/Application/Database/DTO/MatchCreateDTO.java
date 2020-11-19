package Application.Database.DTO;

public class MatchCreateDTO {

    private String score;

    public MatchCreateDTO(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }
}
