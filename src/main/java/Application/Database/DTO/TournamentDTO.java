package Application.Database.DTO;

import Application.Database.Enity.Club;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Singles;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class TournamentDTO {

    private final int id;

    private final Date date;

    private final String name;

    private final String category;

    private final int club;

    private final List<Integer> singlesIDs;

    private final List<Integer> doublesIDs;

    private final List<Integer> playerIDs;

    public TournamentDTO(int id, Date date, String name, String category, int club,
                         List<Integer> singlesIDs, List<Integer> doublesIDs,
                         List<Integer> playerIDs) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.category = category;
        this.club = club;
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
