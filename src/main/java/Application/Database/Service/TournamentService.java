package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.Enity.*;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class TournamentService extends BaseService implements TournamentServiceInterface {

    @Override
    public Optional<Tournament> findById(Integer id){
        return tournamentRepository.findById(id);
    }

    @Override
    public Optional<TournamentDTO> findByIdAsDTO(Integer id){
        return toDTO(findById(id));
    }

    @Override
    public Page<TournamentDTO> findAll(Pageable pageable) {
        return new PageImpl<>(tournamentRepository.findAll(pageable).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @Override
    public Set<Player> getParticipants(int id) throws InstanceNotFoundException {
        return getIfExists(id, tournamentRepository).getPlayers();
    }

    @Override
    @Transactional
    public TournamentDTO create(TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
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
        return toDTO(tournamentRepository.save(new Tournament(tournamentCreateDTO.getDate(),
                tournamentCreateDTO.getName(), tournamentCreateDTO.getCategory(), club, singles,
                doubles, players)));
    }

    @Override
    @Transactional
    public TournamentDTO update(Integer id, TournamentCreateDTO tournamentCreateDTO) throws InstanceNotFoundException{
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
        return toDTO(tournament);
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Tournament tournament = getIfExists(id, tournamentRepository);
        tournamentRepository.delete(tournament);
    }

    private TournamentDTO toDTO(Tournament tournament){
        return new TournamentDTO(tournament.getId(), tournament.getDate(), tournament.getName(),
                tournament.getCategory(), tournament.getClub().getId(),
                tournament.getSingles().stream().map(Match::getId).collect(Collectors.toSet()),
                tournament.getDoubles().stream().map(Match::getId).collect(Collectors.toSet()),
                tournament.getPlayers().stream().map(Player::getId).collect(Collectors.toSet()));
    }

    private Optional<TournamentDTO> toDTO(Optional<Tournament> tournament){
        if(tournament.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(tournament.get()));
    }
}
