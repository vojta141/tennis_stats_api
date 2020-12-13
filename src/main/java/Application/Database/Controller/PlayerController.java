package Application.Database.Controller;

import Application.Database.DTO.*;
import Application.Database.DTOAssemblers.DoublesDTOAssembler;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.DTOAssemblers.SinglesDTOAssembler;
import Application.Database.DTOAssemblers.TournamentDTOAssembler;
import Application.Database.Enity.Player;
import Application.Database.Enity.Tournament;
import Application.Database.Service.PlayerService;
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
@RequestMapping("/player")
public class PlayerController extends BaseController<Player, PlayerCreateDTO, PlayerDTO> {

    private final PlayerService playerService;
    private final TournamentService tournamentService;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final TournamentDTOAssembler tournamentDTOAssembler;
    private final SinglesDTOAssembler singlesDTOAssembler;
    private final DoublesDTOAssembler doublesDTOAssembler;
    private final PagedResourcesAssembler<Player> pagedResourcesAssembler;
    private final PagedResourcesAssembler<Tournament> pagedResourcesAssemblerTournament;


    public PlayerController(ServiceInterface<Player, PlayerCreateDTO, Integer> service,
                            RepresentationModelAssembler<Player, PlayerDTO> assembler,
                            PlayerService playerService, TournamentService tournamentService, PlayerDTOAssembler playerDTOAssembler,
                            TournamentDTOAssembler tournamentDTOAssembler, SinglesDTOAssembler singlesDTOAssembler, DoublesDTOAssembler doublesDTOAssembler, PagedResourcesAssembler<Player> pagedResourcesAssembler,
                            PagedResourcesAssembler<Tournament> pagedResourcesAssemblerTournament) {
        super(service, assembler);
        this.playerService = playerService;
        this.tournamentService = tournamentService;
        this.playerDTOAssembler = playerDTOAssembler;
        this.tournamentDTOAssembler = tournamentDTOAssembler;
        this.singlesDTOAssembler = singlesDTOAssembler;
        this.doublesDTOAssembler = doublesDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pagedResourcesAssemblerTournament = pagedResourcesAssemblerTournament;
    }

    @GetMapping("/all")
    public PagedModel<PlayerDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Player> players = playerService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(players, playerDTOAssembler);
    }

    @GetMapping("/{id}/tournaments")
    public PagedModel<TournamentDTO> getPlayerTournaments(@PathVariable int id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        try {
            Page<Tournament> tournaments = playerService.getTournaments(id, PageRequest.of(page, size));
            return pagedResourcesAssemblerTournament.toModel(tournaments, tournamentDTOAssembler);
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{playerId}/tournaments/{tournamentId}/doubles")
    public List<DoublesDTO> getDoublesAtTournament(@PathVariable int playerId, @PathVariable int tournamentId){
        try {
            return tournamentService.getDoublesOfPlayer(tournamentId, playerId).stream()
                    .map(doublesDTOAssembler::toModel).collect(Collectors.toList());
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{playerId}/tournaments/{tournamentId}/singles")
    public List<SinglesDTO> getSinglesAtTournament(@PathVariable int playerId, @PathVariable int tournamentId){
        try {
            return tournamentService.getSinglesOfPlayer(tournamentId, playerId).stream()
                    .map(singlesDTOAssembler::toModel).collect(Collectors.toList());
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
