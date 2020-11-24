package Application.Database.Repository;

import Application.Database.Enity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {

    Optional<Club> findByName(String name);
}
