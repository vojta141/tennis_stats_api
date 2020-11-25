package Application.Database.DTO;

public class SinglesDTO extends MatchDTO{

    private final int winnerId;

    private final int loserId;

    private final int tournamentId;

    public SinglesDTO(int id, String score, int winnerId, int loserId, int tournamentId) {
        super(id, score);
        this.winnerId = winnerId;
        this.loserId = loserId;
        this.tournamentId = tournamentId;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public int getLoserId() {
        return loserId;
    }

    public int getTournamentId() {
        return tournamentId;
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
                    this.loserId == DTO.loserId &&
                    this.winnerId == DTO.winnerId &&
                    this.tournamentId == DTO.tournamentId)
                return true;
        }
        return false;
    }
}
