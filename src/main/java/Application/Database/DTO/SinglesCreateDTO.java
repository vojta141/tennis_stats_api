package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class SinglesCreateDTO {
    private String score;

    private int winner;

    private int loser;

    private int tournament;

    public SinglesCreateDTO(String score, int winner, int loser, int tournament) {
        this.score = score;
        this.winner = winner;
        this.loser = loser;
        this.tournament = tournament;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getLoser() {
        return loser;
    }

    public void setLoser(int loser) {
        this.loser = loser;
    }

    public int getTournament() {
        return tournament;
    }

    public void setTournament(int tournament) {
        this.tournament = tournament;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
