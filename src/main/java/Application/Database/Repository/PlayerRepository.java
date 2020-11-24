package Application.Database.Repository;

import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findAllByName(String name);

    List<Player> findAllByBigPoints(Integer bigPoints);

    List<Player> findAllByBirthDate(Date birthDate);

    @Query(
        "from Player as player where player.club.id = :clubId"
    )
    List<Player> findAllByClubId(int clubId);

}
