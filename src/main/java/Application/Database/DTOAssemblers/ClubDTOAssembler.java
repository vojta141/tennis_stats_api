package Application.Database.DTOAssemblers;

import Application.Database.Controller.ClubController;
import Application.Database.DTO.ClubDTO;
import Application.Database.Enity.Club;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ClubDTOAssembler extends RepresentationModelAssemblerSupport<Club, ClubDTO> {


    public ClubDTOAssembler() {
        super(ClubController.class, ClubDTO.class);
    }

    @Override
    public ClubDTO toModel(Club club) {
        return new ClubDTO(club.getId(), club.getName());
    }
}
