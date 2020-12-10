package Application.Database.Service;

import Application.Database.DTO.PlayerCreateDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.Player;

import java.util.List;

public interface PlayerServiceInterface extends ServiceInterface<Player, PlayerCreateDTO, Integer> {
    List<Player> findAllByClubId(int clubId);
}
