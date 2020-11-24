package Application.Database.Service;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClubServiceInterface extends ServiceInterface<Club, ClubCreateDTO, ClubDTO, Integer> {

    Optional<Club> findByName(String name);
}
