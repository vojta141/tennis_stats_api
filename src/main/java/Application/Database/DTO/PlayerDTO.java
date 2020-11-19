package Application.Database.DTO;

import Application.Database.Enity.Club;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class PlayerDTO {

    private final int id;

    private final String name;

    private final Date birthDate;

    private final int bigPoints;

    private final int clubId;

    private Set<Integer> tournamentIDs;

    public PlayerDTO(int id, String name, Date birthDate, int bigPoints, int clubId, Set<Integer> tournamentIDs) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.clubId = clubId;
        this.tournamentIDs = tournamentIDs;
    }

    public int getId() {
        return id;
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
