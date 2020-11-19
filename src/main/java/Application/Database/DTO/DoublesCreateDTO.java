package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class DoublesCreateDTO extends MatchCreateDTO {
    private final int winner1;

    private final int winner2;

    private final int loser1;

    private final int loser2;

    private final int tournament;

    public DoublesCreateDTO(String score, int winner1, int winner2, int loser1, int loser2, int tournament) {
        super(score);
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.loser1 = loser1;
        this.loser2 = loser2;
        this.tournament = tournament;
    }

    public int getWinner1() {
        return winner1;
    }

    public int getWinner2() {
        return winner2;
    }

    public int getLoser1() {
        return loser1;
    }

    public int getLoser2() {
        return loser2;
    }

    public int getTournament() {
        return tournament;
    }
}
