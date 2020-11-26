package Application.Database.Service;

import Application.Database.DTO.SinglesCreateDTO;
import Application.Database.DTO.SinglesDTO;
import Application.Database.Enity.Player;
import Application.Database.Enity.Singles;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SinglesService extends BaseService implements SinglesServiceInterface {

    @Override
    public Optional<Singles> findById(Integer id){
        return singlesRepository.findById(id);
    }

    @Override
    public Optional<SinglesDTO> findByIdAsDTO(Integer id){
        return toDTO(findById(id));
    }

    @Override
    public Page<SinglesDTO> findAll(Pageable pageable) {
        return new PageImpl<>(singlesRepository.findAll(pageable).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public SinglesDTO create(SinglesCreateDTO singlesCreateDTO) throws InstanceNotFoundException {
        if(singlesCreateDTO.getWinner() == singlesCreateDTO.getLoser())
            throw new InstanceNotFoundException("Players are identical");
        Tournament tournament = getIfExists(singlesCreateDTO.getTournament(), tournamentRepository);
        Player winner = getIfExists(singlesCreateDTO.getWinner(), playerRepository);
        Player loser = getIfExists(singlesCreateDTO.getLoser(), playerRepository);
        return toDTO(singlesRepository.save(new Singles(singlesCreateDTO.getScore(), winner,
                loser, tournament)));
    }

    @Override
    @Transactional
    public SinglesDTO update(Integer id, SinglesCreateDTO singlesCreateDTO) throws InstanceNotFoundException{
        Singles singles = getIfExists(id, singlesRepository);
        if(singlesCreateDTO.getWinner() == singlesCreateDTO.getLoser())
            throw new InstanceNotFoundException("Players are identical");
        Tournament tournament = getIfExists(singlesCreateDTO.getTournament(), tournamentRepository);
        Player winner = getIfExists(singlesCreateDTO.getWinner(), playerRepository);
        Player loser = getIfExists(singlesCreateDTO.getLoser(), playerRepository);
        singles.setTournament(tournament);
        singles.setWinner(winner);
        singles.setLoser(loser);
        return toDTO(singles);
    }

    @Override
    public void remove(Integer id) throws InstanceNotFoundException {
        Singles singles = getIfExists(id, singlesRepository);
        singlesRepository.delete(singles);
    }

    private SinglesDTO toDTO(Singles singles){
        return new SinglesDTO(singles.getId(), singles.getScore(), singles.getWinner().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
    }

    private Optional<SinglesDTO> toDTO(Optional<Singles> singles){
        if(singles.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(singles.get()));
    }
}
