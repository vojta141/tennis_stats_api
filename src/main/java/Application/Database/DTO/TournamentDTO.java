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

    private int id;

    private Date date;

    private String name;

    private String category;

    private Club club;

    private List<Integer> singlesIDs;

    private List<Integer> doublesIDs;

    private List<Integer> playerIDs;

    public TournamentDTO(int id, Date date, String name, String category, Club club,
                         List<Integer> singlesIDs, List<Integer> doublesIDs, List<Integer> playerIDs) {
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

    public Club getClub() {
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
