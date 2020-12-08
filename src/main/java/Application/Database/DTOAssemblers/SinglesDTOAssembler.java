package Application.Database.DTOAssemblers;

import Application.Database.Controller.SinglesController;
import Application.Database.DTO.SinglesDTO;
import Application.Database.Enity.Singles;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class SinglesDTOAssembler extends RepresentationModelAssemblerSupport<Singles, SinglesDTO> {

    SinglesDTOAssembler(){
        super(SinglesController.class, SinglesDTO.class);
    }

    @Override
    public SinglesDTO toModel(Singles singles) {
        return new SinglesDTO(singles.getId(), singles.getScore(), singles.getWinner().getId(),
                singles.getLoser().getId(), singles.getTournament().getId());
    }
}
