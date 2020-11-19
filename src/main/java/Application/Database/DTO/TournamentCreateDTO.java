package Application.Database.DTO;

import Application.Database.Enity.Club;

import java.util.Date;
import java.util.List;

public class TournamentCreateDTO {

    private Date date;

    private String name;

    private String category;

    private int club;

    private List<Integer> singlesIDs;

    private List<Integer> doublesIDs;

    private List<Integer> playerIDs;

    public TournamentCreateDTO() {
    }

    public TournamentCreateDTO(Date date, String name, String category, int club,
                               List<Integer> singlesIDs, List<Integer> doublesIDs,
                               List<Integer> playerIDs) {
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

    public List<Integer> getSinglesIDs() {
        return singlesIDs;
    }

    public void setSinglesIDs(List<Integer> singlesIDs) {
        this.singlesIDs = singlesIDs;
    }

    public List<Integer> getDoublesIDs() {
        return doublesIDs;
    }

    public void setDoublesIDs(List<Integer> doublesIDs) {
        this.doublesIDs = doublesIDs;
    }

    public List<Integer> getPlayerIDs() {
        return playerIDs;
    }

    public void setPlayerIDs(List<Integer> playerIDs) {
        this.playerIDs = playerIDs;
    }
}
