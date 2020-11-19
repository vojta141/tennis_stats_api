package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class SinglesCreateDTO extends MatchCreateDTO {
    private final int winner;

    private final int loser;

    private final int tournament;

    public SinglesCreateDTO(String score, int winner, int loser, int tournament) {
        super(score);
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
