package Application.Database.DTO;

import java.util.Date;
import java.util.Set;

public class TournamentCreateDTO {

    private Date date;

    private String name;

    private String category;

    private int club;

    private Set<Integer> singlesIDs;

    private Set<Integer> doublesIDs;

    private Set<Integer> playerIDs;

    public TournamentCreateDTO() {
    }

    public TournamentCreateDTO(Date date, String name, String category, int club,
                               Set<Integer> singlesIDs, Set<Integer> doublesIDs,
                               Set<Integer> playerIDs) {
        this.date = date;
        this.name = name;
        this.category = category;
        this.club = club;
        this.singlesIDs = singlesIDs;
        this.doublesIDs = doublesIDs;
        this.playerIDs = playerIDs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    public Set<Integer> getSinglesIDs() {
        return singlesIDs;
    }

    public void setSinglesIDs(Set<Integer> singlesIDs) {
        this.singlesIDs = singlesIDs;
    }

    public Set<Integer> getDoublesIDs() {
        return doublesIDs;
    }

    public void setDoublesIDs(Set<Integer> doublesIDs) {
        this.doublesIDs = doublesIDs;
    }

    public Set<Integer> getPlayerIDs() {
        return playerIDs;
    }

    public void setPlayerIDs(Set<Integer> playerIDs) {
        this.playerIDs = playerIDs;
    }
}
