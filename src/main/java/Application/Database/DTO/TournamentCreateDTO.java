package Application.Database.DTO;

import Application.Database.Enity.Club;

import java.util.Date;
import java.util.List;

public class TournamentCreateDTO {

    private final Date date;

    private final String name;

    private final String category;

    private final int club;

    private final List<Integer> singlesIDs;

    private final List<Integer> doublesIDs;

    private final List<Integer> playerIDs;


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

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getClub() {
        return club;
    }

    public List<Integer> getSinglesIDs() {
        return singlesIDs;
    }

    public List<Integer> getDoublesIDs() {
        return doublesIDs;
    }

    public List<Integer> getPlayerIDs() {
        return playerIDs;
    }
}
