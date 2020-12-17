package Application.Database.DTO;

import java.util.Date;
import java.util.Set;

public class PlayerCreateDTO {

    private String name;

    private Date birthDate;

    private int bigPoints;

    private int clubID;

    private Set<Integer> tournamentIDs;

    public PlayerCreateDTO() {
    }

    public PlayerCreateDTO(String name, Date birthDate, int bigPoints,
                           int clubID, Set<Integer> tournamentIDs) {
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.clubID = clubID;
        this.tournamentIDs = tournamentIDs;
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

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public Set<Integer> getTournamentIDs() {
        return tournamentIDs;
    }

    public void setTournamentIDs(Set<Integer> tournamentIDs) {
        this.tournamentIDs = tournamentIDs;
    }
}
