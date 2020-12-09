package Application.Database.Enity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Singles extends BaseEntity{

    @NotNull
    private String score;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "loser_id")
    private Player loser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Singles(@NotNull String score, @NotNull Player winner, @NotNull Player loser,
                   @NotNull Tournament tournament) {
        this.score = score;
        this.winner = winner;
        this.loser = loser;
        this.tournament = tournament;
    }

    public Singles() {

    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getLoser() {
        return loser;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
