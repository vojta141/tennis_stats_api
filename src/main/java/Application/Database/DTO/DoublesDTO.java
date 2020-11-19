package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class DoublesDTO extends MatchDTO{

    private final int winner1Id;

    private final int winner2Id;

    private final int loser1Id;

    private final int loser2Id;

    private final int tournamentId;

    public DoublesDTO(int id, String score, int winner1Id, int winner2Id, int loser1Id, int loser2Id, int tournamentId) {
        super(id, score);
        this.winner1Id = winner1Id;
        this.winner2Id = winner2Id;
        this.loser1Id = loser1Id;
        this.loser2Id = loser2Id;
        this.tournamentId = tournamentId;
    }

    public int getWinner1Id() {
        return winner1Id;
    }

    public int getWinner2Id() {
        return winner2Id;
    }

    public int getLoser1Id() {
        return loser1Id;
    }

    public int getLoser2Id() {
        return loser2Id;
    }

    public int getTournamentId() {
        return tournamentId;
    }
}
