package Application.Database.Enity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class Player extends BaseEntity{

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

    public Player(@NotNull String name, @NotNull Date birthDate, int bigPoints, @NotNull Club club, Set<Tournament> tournaments) {
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.club = club;
        this.tournaments = tournaments;
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
