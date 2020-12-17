package Application.Database.Service;

import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.Enity.*;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PlayerService extends BaseService implements PlayerServiceInterface {

    @Override
    public Optional<Player> findById(Integer id){
        return playerRepository.findById(id);
    }

    @Override
    public Page<Player> findAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    @Override
    public List<Player> findAllByClubId(int clubId){
        return playerRepository.findAllByClubId(clubId);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Player create (PlayerCreateDTO playerCreateDTO) throws InstanceNotFoundException {
        Club club = getIfExists(playerCreateDTO.getClubID(), clubRepository);
        Set<Integer> tournamentIDs = playerCreateDTO.getTournamentIDs();
        Set<Tournament> tournaments = null;
        if(tournamentIDs != null) {
            tournaments = new HashSet<>();
            for (int id : tournamentIDs) {
                tournaments.add(getIfExists(id, tournamentRepository));
            }
        }
        Player player = new Player(playerCreateDTO.getName(), playerCreateDTO.getBirthDate(),
                playerCreateDTO.getBigPoints(), club, tournaments);
        return playerRepository.save(player);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Player update(Integer id, PlayerCreateDTO playerCreateDTO) throws InstanceNotFoundException{
        Player player = getIfExists(id, playerRepository);
        Club club = getIfExists(playerCreateDTO.getClubID(), clubRepository);
        Set<Integer> tournamentIDs = playerCreateDTO.getTournamentIDs();
        Set<Tournament> tournaments = null;
        if(tournamentIDs != null) {
            tournaments = new HashSet<>();
            for (int id2 : tournamentIDs) {
                tournaments.add(getIfExists(id2, tournamentRepository));
            }
        }
        player.setBigPoints(playerCreateDTO.getBigPoints());
        player.setBirthDate(playerCreateDTO.getBirthDate());
        player.setClub(club);
        player.setName(playerCreateDTO.getName());
        player.setTournaments(tournaments);
        return player;
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void remove(Integer id) throws InstanceNotFoundException {
        Player player = getIfExists(id, playerRepository);
        playerRepository.delete(player);
    }

    @Override
    public Page<Tournament> getTournaments(int playerId, Pageable pageable) throws InstanceNotFoundException{
        Player player = getIfExists(playerId, playerRepository);
        List<Tournament> tournaments = new ArrayList<>(player.getTournaments());
        return new PageImpl<>(tournaments, pageable, tournaments.size());
    }
}
