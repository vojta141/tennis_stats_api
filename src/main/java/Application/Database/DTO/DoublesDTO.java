package Application.Database.DTO;

import org.springframework.hateoas.RepresentationModel;

public class DoublesDTO extends RepresentationModel<DoublesDTO> {


    private final int id;

    private final String score;

    private final int winner1ID;

    private final int winner2ID;

    private final int loser1ID;

    private final int loser2ID;

    private final int tournamentId;

    public DoublesDTO(int id, String score, int winner1ID, int winner2ID, int loser1ID, int loser2ID,
                      int tournamentId) {
        this.id = id;
        this.score = score;
        this.winner1ID = winner1ID;
        this.winner2ID = winner2ID;
        this.loser1ID = loser1ID;
        this.loser2ID = loser2ID;
        this.tournamentId = tournamentId;
    }

    public int getWinner1ID() {
        return winner1ID;
    }

    public int getWinner2ID() {
        return winner2ID;
    }

    public int getLoser1ID() {
        return loser1ID;
    }

    public int getLoser2ID() {
        return loser2ID;
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
                    this.loser1ID == DTO.loser1ID &&
                    this.loser2ID == DTO.loser2ID &&
                    this.winner1ID == DTO.winner1ID &&
                    this.winner2ID == DTO.winner2ID &&
                    this.tournamentId == DTO.tournamentId)
                return true;
        }
        return false;
    }
}
