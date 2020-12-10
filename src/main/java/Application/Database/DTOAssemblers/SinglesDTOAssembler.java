package Application.Database.DTOAssemblers;

import Application.Database.Controller.DoublesController;
import Application.Database.Controller.PlayerController;
import Application.Database.Controller.SinglesController;
import Application.Database.Controller.TournamentController;
import Application.Database.DTO.SinglesDTO;
import Application.Database.Enity.Singles;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class SinglesDTOAssembler extends RepresentationModelAssemblerSupport<Singles, SinglesDTO> {

    SinglesDTOAssembler(){
        super(SinglesController.class, SinglesDTO.class);
    }

    @Override
    public SinglesDTO toModel(Singles singles) {
        SinglesDTO singlesDTO = new SinglesDTO(singles.getId(), singles.getScore(), singles.getWinner().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
        singlesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SinglesController.class).findById(singles.getId())).withSelfRel());
        singlesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(singles.getWinner().getId())).withRel("winner"));
        singlesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(singles.getLoser().getId())).withRel("loser"));
        singlesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).findById(singles.getTournament().getId())).withRel("tournament"));
        return singlesDTO;
    }
}
