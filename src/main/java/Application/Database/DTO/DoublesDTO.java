package Application.Database.DTO;

import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class DoublesDTO{


    private final int id;

    private final String score;

    private final int winner1Id;

    private final int winner2Id;

    private final int loser1Id;

    private final int loser2Id;

    private final int tournamentId;

    public DoublesDTO(int id, String score, int winner1Id, int winner2Id,
                      int loser1Id, int loser2Id, int tournamentId) {
        this.id = id;
        this.score = score;
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

    public int getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof DoublesDTO))
            return false;
        else{
            DoublesDTO DTO = (DoublesDTO) o;
            if(this.getId() == DTO.getId() &&
                    this.getScore().equals(DTO.getScore()) &&
                    this.loser1Id == DTO.loser1Id &&
                    this.loser2Id == DTO.loser2Id &&
                    this.winner1Id == DTO.winner1Id &&
                    this.winner2Id == DTO.winner2Id &&
                    this.tournamentId == DTO.tournamentId)
                return true;
        }
        return false;
    }
}
