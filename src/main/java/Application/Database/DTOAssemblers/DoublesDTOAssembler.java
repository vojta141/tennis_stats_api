package Application.Database.DTOAssemblers;

import Application.Database.Controller.ClubController;
import Application.Database.Controller.DoublesController;
import Application.Database.Controller.PlayerController;
import Application.Database.Controller.TournamentController;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class DoublesDTOAssembler extends RepresentationModelAssemblerSupport<Doubles, DoublesDTO> {

    public DoublesDTOAssembler() {
        super(DoublesController.class, DoublesDTO.class);
    }

    @Override
    public DoublesDTO toModel(Doubles doubles) {
        DoublesDTO doublesDTO = new DoublesDTO(doubles.getId(), doubles.getScore(), doubles.getWinner1().getId(),
                doubles.getWinner2().getId(), doubles.getLoser1().getId(), doubles.getLoser2().getId(), doubles.getTournament().getId());
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoublesController.class).findById(doubles.getId())).withSelfRel());
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(doubles.getWinner1().getId())).withRel("winner1"));
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(doubles.getWinner2().getId())).withRel("winner2"));
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(doubles.getLoser1().getId())).withRel("loser1"));
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerController.class).findById(doubles.getLoser2().getId())).withRel("loser2"));
        doublesDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TournamentController.class).findById(doubles.getTournament().getId())).withRel("tournament"));
        return doublesDTO;
    }
}
