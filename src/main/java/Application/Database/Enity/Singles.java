package Application.Database.Enity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Singles extends Match{

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

    public Singles() {
        super();
    }

    public Singles(String score, Player winner, Player loser, Tournament tournament) {
        super(score);
        this.winner = winner;
        this.loser = loser;
        this.tournament = tournament;
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
}
