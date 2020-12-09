package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class DoublesCreateDTO {

    private String score;

    private  int winner1;

    private int winner2;

    private int loser1;

    private int loser2;

    private int tournament;

    public DoublesCreateDTO() {
        super();
    }

    public DoublesCreateDTO(String score, int winner1, int winner2, int loser1,
                            int loser2, int tournament) {
        this.score = score;
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.loser1 = loser1;
        this.loser2 = loser2;
        this.tournament = tournament;
    }

    public int getWinner1() {
        return winner1;
    }

    public void setWinner1(int winner1) {
        this.winner1 = winner1;
    }

    public int getWinner2() {
        return winner2;
    }

    public void setWinner2(int winner2) {
        this.winner2 = winner2;
    }

    public int getLoser1() {
        return loser1;
    }

    public void setLoser1(int loser1) {
        this.loser1 = loser1;
    }

    public int getLoser2() {
        return loser2;
    }

    public void setLoser2(int loser2) {
        this.loser2 = loser2;
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
