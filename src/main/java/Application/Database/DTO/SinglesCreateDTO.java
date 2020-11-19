package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class SinglesCreateDTO extends MatchCreateDTO {
    private Player winner;

    private Player loser;

    private Tournament tournament;

    public SinglesCreateDTO(String score, Player winner, Player loser, Tournament tournament) {
        super(score);
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
