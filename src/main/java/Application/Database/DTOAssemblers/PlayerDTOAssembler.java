package Application.Database.DTOAssemblers;

import Application.Database.Controller.ClubController;
import Application.Database.Controller.DoublesController;
import Application.Database.Controller.PlayerController;
import Application.Database.DTO.PlayerDTO;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        PlayerDTO playerDTO = new PlayerDTO(player.getId(), player.getName(), player.getBirthDate(),
                player.getBigPoints(), player.getClub().getId(),
                tournaments);
        playerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(player.getId())).withSelfRel());
        playerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClubController.class).findById(player.getClub().getId())).withRel("club"));
        playerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).getPlayerTournaments(player.getClub().getId(), 0, 10)).withRel("player's tournaments"));
        return playerDTO;
    }
}
