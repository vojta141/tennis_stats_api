package Application.Database.Controller;

import Application.Database.DTO.PlayerDTO;
import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.DTOAssemblers.TournamentDTOAssembler;
import Application.Database.Enity.Club;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Database.Service.ServiceInterface;
import Application.Database.Service.TournamentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tournament")
public class TournamentController extends BaseController<Tournament, TournamentCreateDTO, TournamentDTO> {

    private final TournamentService tournamentService;
    private final TournamentDTOAssembler tournamentDTOAssembler;
    private final PagedResourcesAssembler<Tournament> pagedResourcesAssembler;

    public TournamentController(ServiceInterface<Tournament, TournamentCreateDTO, Integer> service,
                                RepresentationModelAssembler<Tournament, TournamentDTO> assembler,
                                TournamentService tournamentService, TournamentDTOAssembler tournamentDTOAssembler,
                                PagedResourcesAssembler<Tournament> pagedResourcesAssembler) {
        super(service, assembler);
        this.tournamentService = tournamentService;
        this.tournamentDTOAssembler = tournamentDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    PagedModel<TournamentDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Tournament> tournaments = tournamentService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(tournaments, tournamentDTOAssembler);
    }
}
