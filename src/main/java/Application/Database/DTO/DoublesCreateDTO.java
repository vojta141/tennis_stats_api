package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class DoublesCreateDTO {

    private String score;

    private  int winner1ID;

    private int winner2ID;

    private int loser1ID;

    private int loser2ID;

    private int tournamentID;

    public DoublesCreateDTO() {
        super();
    }

    public DoublesCreateDTO(String score, int winner1ID, int winner2ID, int loser1ID, int loser2ID, int tournamentID) {
        this.score = score;
        this.winner1ID = winner1ID;
        this.winner2ID = winner2ID;
        this.loser1ID = loser1ID;
        this.loser2ID = loser2ID;
        this.tournamentID = tournamentID;
    }

    public int getWinner1ID() {
        return winner1ID;
    }

    public void setWinner1ID(int winner1ID) {
        this.winner1ID = winner1ID;
    }

    public int getWinner2ID() {
        return winner2ID;
    }

    public void setWinner2ID(int winner2ID) {
        this.winner2ID = winner2ID;
    }

    public int getLoser1ID() {
        return loser1ID;
    }

    public void setLoser1ID(int loser1ID) {
        this.loser1ID = loser1ID;
    }

    public int getLoser2ID() {
        return loser2ID;
    }

    public void setLoser2ID(int loser2ID) {
        this.loser2ID = loser2ID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
