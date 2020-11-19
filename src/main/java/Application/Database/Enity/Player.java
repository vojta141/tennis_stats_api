package Application.Database.Enity;

import com.sun.istack.NotNull;

import javax.persistence.*;
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
    @ManyToOne(fetch = FetchType.LAZY)
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
}
