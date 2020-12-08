package Application.Database.DTOAssemblers;

import Application.Database.Controller.DoublesController;
import Application.Database.DTO.DoublesDTO;
import Application.Database.Enity.Doubles;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DoublesDTOAssembler extends RepresentationModelAssemblerSupport<Doubles, DoublesDTO> {

    public DoublesDTOAssembler() {
        super(DoublesController.class, DoublesDTO.class);
    }

    @Override
    public DoublesDTO toModel(Doubles doubles) {
        return new DoublesDTO(doubles.getId(), doubles.getScore(), doubles.getWinner1().getId(),
                doubles.getWinner2().getId(), doubles.getLoser1().getId(), doubles.getLoser2().getId(), doubles.getTournament().getId());
    }
}
