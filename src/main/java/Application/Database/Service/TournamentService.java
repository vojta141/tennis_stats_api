package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.Enity.*;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TournamentService extends BaseService implements TournamentServiceInterface {

    @Override
    public Optional<Tournament> findById(Integer id){
        return tournamentRepository.findById(id);
    }

    @Override
    public Page<Tournament> findAll(Pageable pageable) {
        return tournamentRepository.findAll(pageable);
    }

    @Override
    public Set<Player> getParticipants(int id) throws InstanceNotFoundException {
        return getIfExists(id, tournamentRepository).getPlayers();
    }

    @Override
    @Transactional
    public Tournament create(TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
        Club club = getIfExists(tournamentCreateDTO.getClub(), clubRepository);
        Set<Doubles> doubles = null;
        if (tournamentCreateDTO.getDoublesIDs() != null) {
            doubles = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getDoublesIDs())
                doubles.add(getIfExists(id2, doublesRepository));
        }
        Set<Singles> singles = null;
        if (tournamentCreateDTO.getSinglesIDs() != null) {
            singles = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getSinglesIDs())
                singles.add(getIfExists(id2, singlesRepository));
        }
        Set<Player> players = null;
        if (tournamentCreateDTO.getPlayerIDs() != null) {
            players = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getPlayerIDs())
                players.add(getIfExists(id2, playerRepository));
        }
        return tournamentRepository.save(new Tournament(tournamentCreateDTO.getDate(),
                tournamentCreateDTO.getName(), tournamentCreateDTO.getCategory(), club, singles,
                doubles, players));
    }

    @Override
    @Transactional
    public Tournament update(Integer id, TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(id, tournamentRepository);
        Club club = getIfExists(tournamentCreateDTO.getClub(), clubRepository);
        Set<Doubles> doubles = null;
        if (tournamentCreateDTO.getDoublesIDs() != null) {
            doubles = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getDoublesIDs())
                doubles.add(getIfExists(id2, doublesRepository));
        }
        Set<Singles> singles = null;
        if (tournamentCreateDTO.getSinglesIDs() != null) {
            singles = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getSinglesIDs())
                singles.add(getIfExists(id2, singlesRepository));
        }
        Set<Player> players = null;
        if (tournamentCreateDTO.getPlayerIDs() != null) {
            players = new HashSet<>();
            for (int id2 : tournamentCreateDTO.getPlayerIDs())
                players.add(getIfExists(id2, playerRepository));
        }
        tournament.setCategory(tournamentCreateDTO.getCategory());
        tournament.setDate(tournamentCreateDTO.getDate());
        tournament.setName(tournamentCreateDTO.getName());
        tournament.setClub(club);
        tournament.setDoubles(doubles);
        tournament.setSingles(singles);
        tournament.setPlayers(players);
        return tournament;
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Tournament tournament = getIfExists(id, tournamentRepository);
        tournamentRepository.delete(tournament);
    }
}
