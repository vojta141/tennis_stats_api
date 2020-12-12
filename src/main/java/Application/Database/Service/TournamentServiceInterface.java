package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Singles;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface TournamentServiceInterface extends ServiceInterface<Tournament, TournamentCreateDTO, Integer> {
    Set<Player> getParticipants(int id) throws InstanceNotFoundException;

    List<Tournament> findTournamentsByClubId(int clubId) throws InstanceNotFoundException;

    Page<Doubles> getDoubles(int tournamentId, Pageable pageable) throws InstanceNotFoundException;

    Page<Singles> getSingles(int tournamentId, Pageable pageable) throws InstanceNotFoundException;
}
