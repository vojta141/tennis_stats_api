package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class DoublesDTO extends MatchDTO{

    private Player winner1;

    private Player winner2;

    private Player loser1;

    private Player loser2;

    private Tournament tournament;

    public DoublesDTO(int id, String score, Player winner1, Player winner2,
                      Player loser1, Player loser2, Tournament tournament) {
        super(id, score);
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.loser1 = loser1;
        this.loser2 = loser2;
        this.tournament = tournament;
    }

    public Player getWinner1() {
        return winner1;
    }

    public Player getWinner2() {
        return winner2;
    }

    public Player getLoser1() {
        return loser1;
    }

    public Player getLoser2() {
        return loser2;
    }

    public Tournament getTournament() {
        return tournament;
    }
}
