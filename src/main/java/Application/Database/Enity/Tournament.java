package Application.Database.Enity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Tournament extends BaseEntity{

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
    private Set<Singles> singles;

    @OneToMany(mappedBy = "tournament")
    private Set<Doubles> doubles;

    @ManyToMany
    @JoinTable(name = "participation",
                joinColumns = @JoinColumn(name = "tournament_id"),
                inverseJoinColumns = @JoinColumn(name = "players_id"))
    private Set<Player> players;

    public Tournament()
    {

    }

    public Tournament(Date date, String name, String category, Club club) {
        this.date = date;
        this.name = name;
        this.category = category;
        this.club = club;
    }

    public Tournament(@NotNull Date date, @NotNull String name, @NotNull String category, @NotNull Club club,
                      Set<Singles> singles, Set<Doubles> doubles, Set<Player> players) {
        this.date = date;
        this.name = name;
        this.category = category;
        this.club = club;
        this.singles = singles;
        this.doubles = doubles;
        this.players = players;
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

    public Set<Singles> getSingles() {
        return singles;
    }

    public void setSingles(Set<Singles> singles) {
        this.singles = singles;
    }

    public Set<Doubles> getDoubles() {
        return doubles;
    }

    public void setDoubles(Set<Doubles> doubles) {
        this.doubles = doubles;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
