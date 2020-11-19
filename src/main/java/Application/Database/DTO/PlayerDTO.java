package Application.Database.DTO;

import Application.Database.Enity.Club;
import Application.Database.Enity.Tournament;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class PlayerDTO {

    private int id;

    private String name;

    private Date birthDate;

    private int bigPoints;

    private Club club;

    private Set<Integer> tournamentIDs;

    public PlayerDTO(int id, String name, Date birthDate, int bigPoints,
                     Club club, Set<Integer> tournamentIDs) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.bigPoints = bigPoints;
        this.club = club;
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

    public Club getClub() {
        return club;
    }

    public Set<Integer> getTournamentIDs() {
        return tournamentIDs;
    }
}
