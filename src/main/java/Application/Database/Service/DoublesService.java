package Application.Database.Service;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoublesService extends BaseService implements DoublesServiceInterface {

    public Page<Doubles> findAll(Pageable pageable){
        return doublesRepository.findAll(pageable);
    }

    @Override
    public Optional<Doubles> findById(Integer id){
        return this.doublesRepository.findById(id);
    }

    @Override
    public List<Doubles> findByWinnerId(int winnerID){
        return doublesRepository.findByWinner(winnerID);
    }

    @Override
    public List<Doubles> findByLoserId(int loserID){
        return doublesRepository.findByLoser(loserID);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Doubles create(DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Map<String, List<Player> > players = validateAndRetrievePlayers(doublesCreateDTO);
        List<Player> winners = players.get("winners");
        List<Player> losers = players.get("losers");
        Tournament tournament = getIfExists(doublesCreateDTO.getTournamentID(), tournamentRepository);
        Doubles doubles = new Doubles(doublesCreateDTO.getScore(), winners.get(0), winners.get(1), losers.get(0), losers.get(1), tournament);
        return doublesRepository.save(doubles);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Doubles update(Integer id, DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Doubles doubles = getIfExists(id, doublesRepository);
        Map<String, List<Player> > players = validateAndRetrievePlayers(doublesCreateDTO);
        List<Player> winners = players.get("winners");
        List<Player> losers = players.get("losers");
        Tournament tournament = getIfExists(doublesCreateDTO.getTournamentID(), tournamentRepository);
        doubles.setLoser1(losers.get(0));
        doubles.setLoser2(losers.get(1));
        doubles.setWinner1(winners.get(0));
        doubles.setWinner2(winners.get(1));
        doubles.setTournament(tournament);
        doubles.setScore(doublesCreateDTO.getScore());
        return doubles;
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void remove(Integer id) throws InstanceNotFoundException {
        Doubles doubles = getIfExists(id, doublesRepository);
        doublesRepository.delete(doubles);
    }

    private Map<String, List<Player>> validateAndRetrievePlayers(DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Set<Integer> players = new HashSet<>();
        players.add(doublesCreateDTO.getWinner1ID());
        players.add(doublesCreateDTO.getWinner2ID());
        players.add(doublesCreateDTO.getLoser1ID());
        players.add(doublesCreateDTO.getLoser2ID());
        Map<String, List<Player>> result = new HashMap<>();
        result.put("winners", getPlayers(doublesCreateDTO.getWinner1ID(), doublesCreateDTO.getWinner2ID()));
        result.put("losers", getPlayers(doublesCreateDTO.getLoser1ID(), doublesCreateDTO.getLoser2ID()));
        return result;
    }

    private List<Player> getPlayers(int playerId1, int playerId2) throws InstanceNotFoundException{
        List<Player> players = new ArrayList<>();
        Optional<Player> tmp = playerRepository.findById(playerId1);
        if(tmp.isPresent())
            players.add(tmp.get());
        tmp = playerRepository.findById(playerId2);
        if(tmp.isPresent())
            players.add(tmp.get());
        if(players.size() != 2)
            throw new InstanceNotFoundException("Some players were not found");
        return players;
    }

}
