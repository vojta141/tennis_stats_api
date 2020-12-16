package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.Enity.*;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.annotation.Secured;
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
    public List<Tournament> findTournamentsByClubId(int clubId) throws InstanceNotFoundException{
        return tournamentRepository.findAllByClubId(clubId);
    }

    @Override
    public Page<Doubles> getDoubles(int tournamentId, Pageable pageable) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(tournamentId, tournamentRepository);
        List<Doubles> doubles = new ArrayList<>(tournament.getDoubles());
        return new PageImpl<>(doubles, pageable, doubles.size());
    }

    @Override
    public Page<Singles> getSingles(int tournamentId, Pageable pageable) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(tournamentId, tournamentRepository);
        List<Singles> singles = new ArrayList<>(tournament.getSingles());
        return new PageImpl<>(singles, pageable, singles.size());
    }

    @Override
    public List<Doubles> getDoublesOfPlayer(int tournamentId, int playerId) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(tournamentId, tournamentRepository);
        List<Doubles> doubles = new ArrayList<>(tournament.getDoubles());
        List<Doubles> result = new ArrayList<>();
        for(Doubles match : doubles){
            if(match.getWinner1().getId() == playerId || match.getLoser1().getId() == playerId
            || match.getWinner2().getId() == playerId || match.getLoser2().getId() == playerId)
                result.add(match);
        }
        return result;
    }

    @Override
    public List<Singles> getSinglesOfPlayer(int tournamentId, int playerId) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(tournamentId, tournamentRepository);
        List<Singles> singles = new ArrayList<>(tournament.getSingles());
        List<Singles> result = new ArrayList<>();
        for(Singles match : singles){
            if(match.getWinner().getId() == playerId || match.getLoser().getId() == playerId)
                result.add(match);
        }
        return result;
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Tournament create(TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
        Club club = getIfExists(tournamentCreateDTO.getClubID(), clubRepository);
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
    @Secured("ROLE_ADMIN")
    public Tournament update(Integer id, TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
        Tournament tournament = getIfExists(id, tournamentRepository);
        Club club = getIfExists(tournamentCreateDTO.getClubID(), clubRepository);
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
    @Transactional
    @Secured("ROLE_ADMIN")
    public void remove(Integer id) throws InstanceNotFoundException {
        Tournament tournament = getIfExists(id, tournamentRepository);
        tournamentRepository.delete(tournament);
    }

}
