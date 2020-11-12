package Application.Database.Enity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Doubles extends Match {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "winner1_id")
    private Player winner1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "winner2_id")
    private Player winner2;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "loser1_id")
    private Player loser1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "loser2_id")
    private Player loser2;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Doubles(String score, Player winner1, Player winner2, Player loser1, Player loser2, Tournament tournament) {
        super(score);
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.loser1 = loser1;
        this.loser2 = loser2;
        this.tournament = tournament;
    }

    public Doubles() {
        super();

    }

    public Player getWinner1() {
        return winner1;
    }

    public void setWinner1(Player winner1) {
        this.winner1 = winner1;
    }

    public Player getWinner2() {
        return winner2;
    }

    public void setWinner2(Player winner2) {
        this.winner2 = winner2;
    }

    public Player getLoser1() {
        return loser1;
    }

    public void setLoser1(Player loser1) {
        this.loser1 = loser1;
    }

    public Player getLoser2() {
        return loser2;
    }

    public void setLoser2(Player loser2) {
        this.loser2 = loser2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
