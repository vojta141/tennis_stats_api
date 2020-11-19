package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class SinglesDTO extends MatchDTO{

    private Player winner;

    private Player loser;

    private Tournament tournament;

    public SinglesDTO(int id, String score, Player winner, Player loser, Tournament tournament) {
        super(id, score);
        this.winner = winner;
        this.loser = loser;
        this.tournament = tournament;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public Tournament getTournament() {
        return tournament;
    }
}
