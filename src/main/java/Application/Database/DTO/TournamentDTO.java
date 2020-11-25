package Application.Database.DTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TournamentDTO {

    private final int id;

    private final Date date;

    private final String name;

    private final String category;

    private final int clubId;

    private final Set<Integer> singlesIDs;

    private final Set<Integer> doublesIDs;

    private final Set<Integer> playerIDs;

    public TournamentDTO(int id, Date date, String name, String category, int clubId,
                         Set<Integer> singlesIDs, Set<Integer> doublesIDs, Set<Integer> playerIDs) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.category = category;
        this.clubId = clubId;
        this.singlesIDs = singlesIDs;
        this.doublesIDs = doublesIDs;
        this.playerIDs = playerIDs;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getClubId() {
        return clubId;
    }

    public Set<Integer> getSinglesIDs() {
        return singlesIDs;
    }

    public Set<Integer> getDoublesIDs() {
        return doublesIDs;
    }

    public Set<Integer> getPlayerIDs() {
        return playerIDs;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof SinglesDTO))
            return false;
        else{
            TournamentDTO DTO = (TournamentDTO) o;
            if(this.id == DTO.id &&
                    this.name.equals(DTO.name) &&
                    this.date.equals(DTO.date) &&
                    this.category.equals(DTO.category) &&
                    this.singlesIDs.size() == DTO.singlesIDs.size() &&
                    this.singlesIDs.containsAll(DTO.singlesIDs) &&
                    this.doublesIDs.size() == DTO.doublesIDs.size() &&
                    this.doublesIDs.containsAll(DTO.doublesIDs) &&
                    this.playerIDs.size() == DTO.playerIDs.size() &&
                    this.playerIDs.containsAll(DTO.playerIDs))
                return true;
        }
        return false;
    }
}
