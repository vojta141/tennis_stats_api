package Application.Database.DTO;

import org.springframework.hateoas.RepresentationModel;

public class SinglesDTO extends RepresentationModel<SinglesDTO> {

    private int id;

    private String score;

    private final int winnerID;

    private final int loserID;

    private final int tournamentID;

    public SinglesDTO(int id, String score, int winnerID, int loserID, int tournamentID) {
        this.id = id;
        this.score = score;
        this.winnerID = winnerID;
        this.loserID = loserID;
        this.tournamentID = tournamentID;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public int getLoserID() {
        return loserID;
    }

    public int getTournamentID() {
        return tournamentID;
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
        if(!(o instanceof SinglesDTO))
            return false;
        else{
            SinglesDTO DTO = (SinglesDTO) o;
            if(this.getId() == DTO.getId() &&
                    this.getScore().equals(DTO.getScore()) &&
                    this.loserID == DTO.loserID &&
                    this.winnerID == DTO.winnerID &&
                    this.tournamentID == DTO.tournamentID)
                return true;
        }
        return false;
    }
}
