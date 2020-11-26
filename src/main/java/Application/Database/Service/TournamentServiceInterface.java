package Application.Database.Service;

import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Exceptions.InstanceNotFoundException;

import java.util.List;
import java.util.Set;

public interface TournamentServiceInterface extends ServiceInterface<Tournament, TournamentCreateDTO, TournamentDTO, Integer> {
    Set<Player> getParticipants(int id) throws InstanceNotFoundException;
}
