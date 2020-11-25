package Application.Database.Enity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Match extends BaseEntity {
    @NotNull
    private String score;

    public Match() {

    }

    public Match(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /*public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }*/
}
