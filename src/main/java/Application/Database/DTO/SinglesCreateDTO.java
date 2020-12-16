package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;

public class SinglesCreateDTO {
    private String score;

    private int winnerID;

    private int loserID;

    private int tournamentID;

    public SinglesCreateDTO(String score, int winnerID, int loserID, int tournamentID) {
        this.score = score;
        this.winnerID = winnerID;
        this.loserID = loserID;
        this.tournamentID = tournamentID;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }

    public int getLoserID() {
        return loserID;
    }

    public void setLoserID(int loserID) {
        this.loserID = loserID;
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
