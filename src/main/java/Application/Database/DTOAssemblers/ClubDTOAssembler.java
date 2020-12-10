package Application.Database.DTOAssemblers;

import Application.Database.Controller.ClubController;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ClubDTOAssembler extends RepresentationModelAssemblerSupport<Club, ClubDTO> {


    public ClubDTOAssembler() {
        super(ClubController.class, ClubDTO.class);
    }

    @Override
    public ClubDTO toModel(Club club) {
        ClubDTO clubDTO = new ClubDTO(club.getId(), club.getName());
        clubDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClubController.class).findById(club.getId())).withSelfRel());
        return clubDTO;
    }
}
