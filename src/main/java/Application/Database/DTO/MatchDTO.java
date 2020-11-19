package Application.Database.DTO;

import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MatchDTO {

    private int id;

    private String score;

    public MatchDTO(int id, String score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getScore() {
        return score;
    }
}
