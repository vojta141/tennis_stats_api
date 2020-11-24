package Application.Database.Repository;

import Application.Database.Enity.Player;
import Application.Database.Enity.Singles;
import Application.Database.Enity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinglesRepository extends JpaRepository<Singles, Integer> {

    List<Singles> findByWinner(Player winner);

    List<Singles> findByLoser(Player loser);

    Optional<Singles> findByTournament(Tournament tournament);
}
