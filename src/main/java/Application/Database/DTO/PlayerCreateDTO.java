package Application.Database.DTO;

import Application.Database.Enity.Club;

import java.util.Date;
import java.util.Set;

public class PlayerCreateDTO {

    private String name;

    private Date birthDate;

    private int bigPoints;

    private Club club;

    private Set<Integer> tournamentIDs;

    public PlayerCreateDTO(String name, Date birthDate, int bigPoints,
                     Club club, Set<Integer> tournamentIDs) {
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.club = club;
        this.tournamentIDs = tournamentIDs;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getBigPoints() {
        return bigPoints;
    }

    public Club getClub() {
        return club;
    }

    public Set<Integer> getTournamentIDs() {
        return tournamentIDs;
    }
}
