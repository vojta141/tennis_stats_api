package Application.Database.Service;

import Application.Database.Enity.*;
import Application.Database.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaseService implements BaseServiceInterface {

    protected ClubRepository clubRepository;
    protected DoublesRepository doublesRepository;
    protected SinglesRepository singlesRepository;
    protected PlayerRepository playerRepository;
    protected TournamentRepository tournamentRepository;

    @Autowired
    public void setClubRepository(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }
    @Autowired
    public void setDoublesRepository(DoublesRepository doublesRepository) {
        this.doublesRepository = doublesRepository;
    }
    @Autowired
    public void setSinglesRepository(SinglesRepository singlesRepository) {
        this.singlesRepository = singlesRepository;
    }
    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    @Autowired
    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public <E> E getIfExists(int id, JpaRepository<E,Integer> repository) throws Exception{
        Optional<E> e = repository.findById(id);
        if(e.isEmpty())
            throw new Exception("id: " + id + " does not exist");
        return e.get();
    }
}
