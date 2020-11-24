package Application.Database.Repository;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoublesRepository extends JpaRepository<Doubles, Integer> {

    @Query(
        "from Doubles as doubles where doubles.winner1.id = :id or doubles.winner2.id = :id"
    )
    List<Doubles> findByWinner(Integer id);

    @Query(
        "from Doubles as doubles where doubles.loser1 = :id or doubles.loser2 = :id"
    )
    Optional<Doubles> findByLoser(Integer id);

}
