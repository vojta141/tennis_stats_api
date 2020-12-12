package Application.Database.Repository;

import Application.Database.Enity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    List<Tournament> findAllByDate(Date date);

    List<Tournament> findAllByClubId(int id);

}
