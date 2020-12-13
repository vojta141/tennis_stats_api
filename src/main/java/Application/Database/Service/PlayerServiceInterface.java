package Application.Database.Service;

import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerServiceInterface extends ServiceInterface<Player, PlayerCreateDTO, Integer> {
    List<Player> findAllByClubId(int clubId);

    Page<Tournament> getTournaments(int playerId, Pageable pageable) throws InstanceNotFoundException;
}
