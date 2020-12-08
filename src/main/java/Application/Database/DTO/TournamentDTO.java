package Application.Database.DTO;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TournamentDTO extends RepresentationModel<TournamentDTO> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentDTO that = (TournamentDTO) o;
        return id == that.id &&
                clubId == that.clubId &&
                date.equals(that.date) &&
                name.equals(that.name) &&
                category.equals(that.category) &&
                Objects.equals(singlesIDs, that.singlesIDs) &&
                Objects.equals(doublesIDs, that.doublesIDs) &&
                Objects.equals(playerIDs, that.playerIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name, category, clubId, singlesIDs, doublesIDs, playerIDs);
    }
}
