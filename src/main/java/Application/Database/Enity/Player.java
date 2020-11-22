package Application.Database.Enity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private Date birthDate;

    private int bigPoints;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany(mappedBy = "players")
    private Set<Tournament> tournaments;

    public Player() {

    }

    public Player(String name, Date birthDate, Club club) {
        this.name = name;
        this.birthDate = birthDate;
        this.club = club;
    }

    public Player(String name, Date birthDate, int bigPoints, Club club) {
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.club = club;
    }

    public Player(int id, String name, Date birthDate, int bigPoints, Club club,
                  Set<Tournament> tournaments) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.club = club;
        this.tournaments = tournaments;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getBigPoints() {
        return bigPoints;
    }

    public void setBigPoints(int bigPoints) {
        this.bigPoints = bigPoints;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
