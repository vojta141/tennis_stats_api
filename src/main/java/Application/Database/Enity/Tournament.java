package Application.Database.Enity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private Date date;

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "tournament")
    private List<Singles> singles;

    @OneToMany(mappedBy = "tournament")
    private List<Doubles> doubles;

    @ManyToMany
    @JoinTable(name = "participation",
                joinColumns = @JoinColumn(name = "tournament_id"),
                inverseJoinColumns = @JoinColumn(name = "players_id"))
    private List<Player> players;

    public Tournament()
    {

    }

    public Tournament(Date date, String name, String category, Club club) {
        this.date = date;
        this.name = name;
        this.category = category;
        this.club = club;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Singles> getSingles() {
        return singles;
    }

    public void setSingles(List<Singles> singles) {
        this.singles = singles;
    }

    public List<Doubles> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Doubles> doubles) {
        this.doubles = doubles;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
