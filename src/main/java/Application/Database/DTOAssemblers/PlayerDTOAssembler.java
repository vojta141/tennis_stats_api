package Application.Database.DTOAssemblers;

import Application.Database.Controller.PlayerController;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerDTOAssembler extends RepresentationModelAssemblerSupport<Player, PlayerDTO> {

    public PlayerDTOAssembler(){
        super(PlayerController.class, PlayerDTO.class);
    }

    @Override
    public PlayerDTO toModel(Player player) {
        Set<Integer> tournaments = null;
        if(player.getTournaments() != null)
            tournaments = player.getTournaments().stream().
                    map(Tournament::getId).collect(Collectors.toSet());
        return new PlayerDTO(player.getId(), player.getName(), player.getBirthDate(),
                player.getBigPoints(), player.getClub().getId(),
                tournaments);
    }
}
