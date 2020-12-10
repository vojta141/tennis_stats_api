package Application.Database.Controller;

import Application.Database.DTO.PlayerDTO;
import Application.Database.DTO.TournamentCreateDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.DTOAssemblers.TournamentDTOAssembler;
import Application.Database.Enity.Tournament;
import Application.Database.Service.ServiceInterface;
import Application.Database.Service.TournamentService;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tournament")
public class TournamentController extends BaseController<Tournament, TournamentCreateDTO, TournamentDTO> {

    private final TournamentService tournamentService;
    private final TournamentDTOAssembler tournamentDTOAssembler;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final PagedResourcesAssembler<Tournament> pagedResourcesAssembler;

    public TournamentController(ServiceInterface<Tournament, TournamentCreateDTO, Integer> service,
                                RepresentationModelAssembler<Tournament, TournamentDTO> assembler,
                                TournamentService tournamentService, TournamentDTOAssembler tournamentDTOAssembler,
                                PlayerDTOAssembler playerDTOAssembler, PagedResourcesAssembler<Tournament> pagedResourcesAssembler) {
        super(service, assembler);
        this.tournamentService = tournamentService;
        this.tournamentDTOAssembler = tournamentDTOAssembler;
        this.playerDTOAssembler = playerDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public PagedModel<TournamentDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Tournament> tournaments = tournamentService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(tournaments, tournamentDTOAssembler);
    }

    @GetMapping("/{id}/participants")
    public List<PlayerDTO> getParticipants(@PathVariable int id){
        try {
            return tournamentService.getParticipants(id).stream().map(playerDTOAssembler::toModel).collect(Collectors.toList());
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tournament with given ID not found");
        }
    }
}
