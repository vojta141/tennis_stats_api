package Application.Database.DTO;

import Application.Database.Enity.Club;

import java.util.Date;
import java.util.Set;

public class PlayerCreateDTO {

    private final String name;

    private final Date birthDate;

    private final int bigPoints;

    private final int clubId;

    private final Set<Integer> tournamentIDs;

    public PlayerCreateDTO(String name, Date birthDate, int bigPoints,
                           int clubId, Set<Integer> tournamentIDs) {
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.clubId = clubId;
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

    public int getClubId() {
        return clubId;
    }

    public Set<Integer> getTournamentIDs() {
        return tournamentIDs;
    }
}
