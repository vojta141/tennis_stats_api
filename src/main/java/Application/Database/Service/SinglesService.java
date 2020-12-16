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
import org.springframework.security.access.annotation.Secured;
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
    public Page<Singles> findAll(Pageable pageable) {
        return singlesRepository.findAll(pageable);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Singles create(SinglesCreateDTO singlesCreateDTO) throws InstanceNotFoundException {
        if(singlesCreateDTO.getWinnerID() == singlesCreateDTO.getLoserID())
            throw new InstanceNotFoundException("Players are identical");
        Tournament tournament = getIfExists(singlesCreateDTO.getTournamentID(), tournamentRepository);
        Player winner = getIfExists(singlesCreateDTO.getWinnerID(), playerRepository);
        Player loser = getIfExists(singlesCreateDTO.getLoserID(), playerRepository);
        return singlesRepository.save(new Singles(singlesCreateDTO.getScore(), winner,
                loser, tournament));
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public Singles update(Integer id, SinglesCreateDTO singlesCreateDTO) throws InstanceNotFoundException{
        Singles singles = getIfExists(id, singlesRepository);
        if(singlesCreateDTO.getWinnerID() == singlesCreateDTO.getLoserID())
            throw new InstanceNotFoundException("Players are identical");
        Tournament tournament = getIfExists(singlesCreateDTO.getTournamentID(), tournamentRepository);
        Player winner = getIfExists(singlesCreateDTO.getWinnerID(), playerRepository);
        Player loser = getIfExists(singlesCreateDTO.getLoserID(), playerRepository);
        singles.setTournament(tournament);
        singles.setWinner(winner);
        singles.setLoser(loser);
        return singles;
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void remove(Integer id) throws InstanceNotFoundException {
        Singles singles = getIfExists(id, singlesRepository);
        singlesRepository.delete(singles);
    }
}
