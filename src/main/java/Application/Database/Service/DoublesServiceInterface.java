package Application.Database.Service;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;

import java.util.List;
import java.util.Optional;

public interface DoublesServiceInterface extends ServiceInterface<Doubles, DoublesCreateDTO, DoublesDTO, Integer>{

    List<Doubles> findByWinnerId(int winnerID);

    List<DoublesDTO> findByWinnerIdAsDTO(int winnerID);
}
