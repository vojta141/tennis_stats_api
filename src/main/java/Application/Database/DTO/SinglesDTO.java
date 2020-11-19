package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class SinglesDTO extends MatchDTO{

    private final int winner;

    private final int loser;

    private final int tournament;

    public SinglesDTO(int id, String score, int winner, int loser, int tournament) {
        super(id, score);
        this.winner = winner;
        this.loser = loser;
        this.tournament = tournament;
    }

    public int getWinner() {
        return winner;
    }

    public int getLoser() {
        return loser;
    }

    public int getTournament() {
        return tournament;
    }
}
