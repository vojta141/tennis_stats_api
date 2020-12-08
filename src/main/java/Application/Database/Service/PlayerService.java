package Application.Database.Service;

import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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
    public Player create (PlayerCreateDTO playerCreateDTO) throws InstanceNotFoundException {
        Club club = getIfExists(playerCreateDTO.getClubId(), clubRepository);
        Set<Integer> tournamentIDs = playerCreateDTO.getTournamentIDs();
        Set<Tournament> tournaments = new HashSet<>();
        for(int id : tournamentIDs){
            tournaments.add(getIfExists(id, tournamentRepository));
        }
        Player player = new Player(playerCreateDTO.getName(), playerCreateDTO.getBirthDate(),
                playerCreateDTO.getBigPoints(), club, tournaments);
        return playerRepository.save(player);
    }

    @Override
    public Player update(Integer id, PlayerCreateDTO playerCreateDTO) throws InstanceNotFoundException{
        Player player = getIfExists(id, playerRepository);
        Club club = getIfExists(playerCreateDTO.getClubId(), clubRepository);
        Set<Tournament> tournaments = new HashSet<>();
        for(int tournamentID : playerCreateDTO.getTournamentIDs()){
            tournaments.add(getIfExists(tournamentID, tournamentRepository));
        }
        player.setBigPoints(playerCreateDTO.getBigPoints());
        player.setBirthDate(playerCreateDTO.getBirthDate());
        player.setClub(club);
        player.setName(playerCreateDTO.getName());
        player.setTournaments(tournaments);
        return player;
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Player player = getIfExists(id, playerRepository);
        playerRepository.delete(player);
    }
}
