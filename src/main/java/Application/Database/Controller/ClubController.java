package Application.Database.Controller;

import Application.Database.DTO.ClubCreateDTO;
import Application.Database.DTO.ClubDTO;
import Application.Database.DTO.PlayerDTO;
import Application.Database.DTO.TournamentDTO;
import Application.Database.DTOAssemblers.ClubDTOAssembler;
import Application.Database.DTOAssemblers.PlayerDTOAssembler;
import Application.Database.DTOAssemblers.TournamentDTOAssembler;
import Application.Database.Enity.Club;
import Application.Database.Enity.Tournament;
import Application.Database.Service.ClubService;
import Application.Database.Service.PlayerService;
import Application.Database.Service.ServiceInterface;
import Application.Database.Service.TournamentService;
import Application.Exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/club")
public class ClubController extends BaseController<Club, ClubCreateDTO, ClubDTO>{

    private final ClubService clubService;
    private final PlayerService playerService;
    private final TournamentService tournamentService;
    private final ClubDTOAssembler clubDTOAssembler;
    private final PlayerDTOAssembler playerDTOAssembler;
    private final TournamentDTOAssembler tournamentDTOAssembler;
    private final PagedResourcesAssembler<Club> pagedResourcesAssembler;


    @Autowired
    public ClubController(ServiceInterface<Club, ClubCreateDTO, Integer> service,
                          RepresentationModelAssembler<Club, ClubDTO> assembler,
                          ClubService clubService, PlayerService playerService,
                          TournamentService tournamentService, ClubDTOAssembler clubDTOAssembler,
                          PlayerDTOAssembler playerDTOAssembler,
                          TournamentDTOAssembler tournamentDTOAssembler, PagedResourcesAssembler<Club> pagedResourcesAssembler) {
        super(service, assembler);
        this.clubService = clubService;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
        this.clubDTOAssembler = clubDTOAssembler;
        this.playerDTOAssembler = playerDTOAssembler;
        this.tournamentDTOAssembler = tournamentDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public PagedModel<ClubDTO> all(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Club> clubs = clubService.findAll(PageRequest.of(page, size));
        return pagedResourcesAssembler.toModel(clubs, clubDTOAssembler);
    }

    @GetMapping("/{clubId}/players")
    public List<PlayerDTO> clubPlayers(@PathVariable int clubId){
        return playerService.findAllByClubId(clubId).stream().map(playerDTOAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{clubId}/tournaments")
    public List<TournamentDTO> clubTournaments(@PathVariable int clubId){
        try{
            return tournamentService.findTournamentsByClubId(clubId)
                    .stream().map(tournamentDTOAssembler::toModel).collect(Collectors.toList());
        } catch (InstanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{clubName}")
    public ClubDTO findByName(@PathVariable String clubName){
        Optional<Club> clubOpt = clubService.findByName(clubName);
        if(clubOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return clubDTOAssembler.toModel(clubOpt.get());

    }
}
