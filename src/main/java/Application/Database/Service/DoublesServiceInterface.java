package Application.Database.Service;

import Application.Database.DTO.DoublesCreateDTO;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;

import java.util.List;
import java.util.Optional;

public interface DoublesServiceInterface extends ServiceInterface<Doubles, DoublesCreateDTO, Integer>{

    List<Doubles> findByWinnerId(int winnerID);

    List<Doubles> findByLoserId(int winnerID);

}
