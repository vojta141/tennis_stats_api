package Application.Database.Controller;

import Application.Database.DTO.*;
import Application.Database.DTOAssemblers.DoublesDTOAssembler;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.DTOAssemblers.SinglesDTOAssembler;
import Application.Database.DTOAssemblers.TournamentDTOAssembler;
import Application.Database.Enity.Doubles;
import Application.Database.Enity.Singles;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("tournament")
public class TournamentController extends BaseController<Tournament, TournamentCreateDTO, TournamentDTO> {

    private final TournamentService tournamentService;
    private final TournamentDTOAssembler tournamentDTOAssembler;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final DoublesDTOAssembler doublesDTOAssembler;
    private final SinglesDTOAssembler singlesDTOAssembler;
    private final PagedResourcesAssembler<Tournament> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Doubles> pagedResourcesAssemblerDoubles;
    private final PagedResourcesAssembler<Singles> pagedResourcesAssemblerSingles;

    public TournamentController(ServiceInterface<Tournament, TournamentCreateDTO, Integer> service,
                                RepresentationModelAssembler<Tournament, TournamentDTO> assembler,
                                TournamentService tournamentService, TournamentDTOAssembler tournamentDTOAssembler,
                                PlayerDTOAssembler playerDTOAssembler, DoublesDTOAssembler doublesDTOAssembler, SinglesDTOAssembler singlesDTOAssembler, PagedResourcesAssembler<Tournament> pagedResourcesAssembler, PagedResourcesAssembler<Doubles> pagedResourcesAssemblerDoubles, PagedResourcesAssembler<Singles> pagedResourcesAssemblerSingles) {
        super(service, assembler);
        this.tournamentService = tournamentService;
        this.tournamentDTOAssembler = tournamentDTOAssembler;
        this.playerDTOAssembler = playerDTOAssembler;
        this.doublesDTOAssembler = doublesDTOAssembler;
        this.singlesDTOAssembler = singlesDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pagedResourcesAssemblerDoubles = pagedResourcesAssemblerDoubles;
        this.pagedResourcesAssemblerSingles = pagedResourcesAssemblerSingles;
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

    @GetMapping("/{id}/doubles")
    public PagedModel<DoublesDTO> getTournamentDoubles(@PathVariable int id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        try {
            Page<Doubles> doubles = tournamentService.getDoubles(id, PageRequest.of(page, size));
            return pagedResourcesAssemblerDoubles.toModel(doubles, doublesDTOAssembler);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/singles")
    public PagedModel<SinglesDTO> getTournamentSingles(@PathVariable int id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        try {
            Page<Singles> singles = tournamentService.getSingles(id, PageRequest.of(page, size));
            return pagedResourcesAssemblerSingles.toModel(singles, singlesDTOAssembler);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
