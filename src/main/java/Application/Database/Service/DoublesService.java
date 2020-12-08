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
    public Doubles create(DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Map<String, List<Player> > players = validateAndRetrievePlayers(doublesCreateDTO);
        List<Player> winners = players.get("winners");
        List<Player> losers = players.get("losers");
        Tournament tournament = getIfExists(doublesCreateDTO.getTournament(), tournamentRepository);
        Doubles doubles = new Doubles(doublesCreateDTO.getScore(), winners.get(0), winners.get(1), losers.get(0), losers.get(1), tournament);
        return doublesRepository.save(doubles);
    }

    @Override
    @Transactional
    public Doubles update(Integer id, DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Doubles doubles = getIfExists(id, doublesRepository);
        Map<String, List<Player> > players = validateAndRetrievePlayers(doublesCreateDTO);
        List<Player> winners = players.get("winners");
        List<Player> losers = players.get("losers");
        Tournament tournament = getIfExists(doublesCreateDTO.getTournament(), tournamentRepository);
        doubles.setLoser1(losers.get(0));
        doubles.setLoser2(losers.get(1));
        doubles.setWinner1(winners.get(0));
        doubles.setWinner2(winners.get(1));
        doubles.setTournament(tournament);
        doubles.setScore(doublesCreateDTO.getScore());
        return doubles;
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Doubles doubles = getIfExists(id, doublesRepository);
        doublesRepository.delete(doubles);
    }

    private Map<String, List<Player>> validateAndRetrievePlayers(DoublesCreateDTO doublesCreateDTO) throws InstanceNotFoundException{
        Set<Integer> players = new HashSet<>();
        players.add(doublesCreateDTO.getWinner1());
        players.add(doublesCreateDTO.getWinner2());
        players.add(doublesCreateDTO.getLoser1());
        players.add(doublesCreateDTO.getLoser2());
        Map<String, List<Player>> result = new HashMap<>();
        result.put("winners", getPlayers(doublesCreateDTO.getWinner1(), doublesCreateDTO.getWinner2()));
        result.put("losers", getPlayers(doublesCreateDTO.getLoser1(), doublesCreateDTO.getLoser2()));
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
